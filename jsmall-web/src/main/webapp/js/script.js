var current_scrollY;


$(window).on('load', function() {
	windowResize();
});

var resizeTimeout;
$(window).on('resize', resizeHandler);
function resizeHandler() {
	windowResize();
	clearTimeout(resizeTimeout);
	resizeTimeout = setTimeout(windowResize(), 10);
}


function windowResize() {
	var windowHeight = $(window).height();
	var windowWidth = $(window).width();
	var header = $('#header');
	var main_content = $('#main-content');
	var footer = $('#footer');
	var footer_wide = $('#footer-wide');
	var footer_sec = $('#footer-sec');
	var main_panel = $('#main-panel');
	var theme_slider = $('#theme-slider');
	var tab_content = $('.tab-content');

	main_content.css('height', 'auto');
	main_panel.css('height', 'auto');
	tab_content.css('height', 'auto');

	var footer_height;
	if(footer.height() === null && footer_sec.height() === null) {
		footer_height = footer_wide.height();
	} else if(footer.height() === null && footer_wide.height() === null) {
		footer_height = footer_sec.height();
	} else {
		footer_height = footer.height();
	}
	if(windowHeight > (main_content.height() + header.height() + footer_height)) {
		main_content.css('height', (windowHeight - header.height() - footer_height) + 'px');
		main_panel.css('height', (windowHeight - header.height() - footer_height - 10) + 'px');
	} else {
		main_content.css('height', "");
		main_panel.css('height', "");
	}

	$('.col-header-table').css('height', '');
	$('.list-box').css('height', '');
	var col_header_table_height = $('.col-header-table').height();
	$('.list-box').css('height', col_header_table_height + 'px');
}

$(document).ready(function() {
	var font_size = null;

	if(font_size != null) {
		$('head').append('<link href="' + font_size + '" rel="stylesheet" />');

		$('.font-size-button').each(function () {
			if($(this).hasClass("btn-color")) $(this).removeClass("btn-color").addClass("btn-default");
			if($(this).attr('data-font-size') == font_size) $(this).removeClass("btn-default").addClass("btn-color");
		});
	}

	$('.font-size-button').click( function(e) {
		e.preventDefault();

		resetStyle();
		$('head').append('<link href="' + $(this).attr('data-font-size') + '" rel="stylesheet" />');

		$('.font-size-button').each( function() {
			if($(this).hasClass("btn-color")) $(this).removeClass("btn-color").addClass("btn-default");
		});

		$(this).removeClass("btn-default").addClass("btn-color");

		$('.information-section > ul > li').each(function() {
			$(this).css('height', ($(this).children('a').height() + 20) + 'px');
		});

		windowResize();

//		localStorage.setItem('font_size', $(this).attr('data-font-size'));
	});

	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		windowResize();
	});

	$('.btn-menu').on('click', function() {
		var url = $(this).attr("href");
		overlay('on');
		setTimeout(function() {
			window.location.href = url;
		}, 100);
		return false;
	});

	var offsetY = -160;
	var time = 500;

	$(".nav-button > a[href^='#']").on('click', function() {
		var target = $(this.hash);
		if(!target.length) return;
		var targetY = target.offset().top + offsetY;
		$('html,body').animate({scrollTop: targetY}, time, 'swing');
		window.history.pushState(null, null, this.hash);
		return false;
	});

	function resetStyle() {
		$('head link[rel="stylesheet"]').each( function() {
			if( $(this).attr('href').toLowerCase().indexOf("font-size") >= 0 )
				$(this).remove();
		});

//		localStorage.removeItem('font_size');
	}
	$(".add-element").on('click', function() {
	     var element = document.createElement('div');
	     element.id = "add";
	     var objBody = document.getElementsByTagName("body").item(0);
	     objBody.appendChild(element);
	 })

	printSignoutQDialog();

	$('#exit-close-btn').on('click', function(e) {
		e.preventDefault();
		$.magnificPopup.close();

		overlay('on');

        $.ajax({
            url: $('#sign-out-btn').data("action"),
            type:'POST',
			dataType:'json'
        }).done(function(data){
			ajaxCommonDone(data, function(data) {
				window.location.href = data.returnUrl;
			});
			overlay('off');

        }).fail(function(data){
			ajaxCommonFail();
			overlay('off');
        });
	});

	var signOutModal = $('#sign-out-dialog').plainDialog({
							overlay: {
								fillColor: '#000',
								opacity: 0.5
							}
						});

	$('#sign-out-btn').on('click', function() {
		setTimeout(function() {
			signOutModal.plainDialog('open');
		}, 50);
	});

	$('[name=download').on('click', function(e) {
		$("#sourceMap").val($(this).attr("data-source-map"));
		$("#seqNumber").val($(this).attr("data-seq-number"));

		$(this).parents('form').attr("action", $(this).attr("data-action")).submit();
	});

	$('[name=downloadTag').on('click', function(e) {
		$("#uploadGetKey").val($(this).attr("data-info"));
		$("#number").val($(this).attr("data-number"));

		$("#temporarilyStoredSeq").val($(this).parent().parent().parent().parent().parent().parent().find('.hidden-temporarily-stored_seq').val());
		$("#existStoredSeq").val($(this).parent().parent().parent().find('.hidden-exist-stored_seq').val());

		$(this).parents('form').attr("action", $(this).attr("data-action")).submit();
	});

	$("[name=ValidationError]").each(function(index, element) {
		$("." + $(element).val().replace(/[.]/g, '\\$&') + "Validate").addClass("has-error");
		$('#errorMessageArea').removeClass('display-none');
	});

	$('[name=back-to-previous-btn]').on('click', function(e) {
		e.preventDefault();
		overlay('on');

		var thisEvent = $(this);
		$("#btnType").val($(this).data("btn-type"));

		if ($('#previousOnFlag').val() == "1") {
			var popUpHtml = '\
				<div id="modal-previous" class="plainmodal-dialog"> \
					<div class="dialog-close-box plaindialog-close"> \
						<i class="fa fa-close"></i> \
					</div> \
					<div class="dialog-body"> \
						<div> \
							<div class="question"></div> \
						</div> \
						<div class="title">終了の確認</div> \
						<div class="msg">本画面で設定した情報は破棄されます。よろしいですか？</div> \
					</div> \
					<div class="dialog-footer"> \
						<ul class="inline"> \
							<li><button type="button" id="dialog-btn" class="btn bg-color plaindialog-close btn-a btn-yes-previous">はい</button></li> \
							<li><button type="button" id="dialog-btn" class="btn bg-color plaindialog-close btn-cancel-previous">いいえ</button></li> \
						</ul> \
					</div> \
				</div>'

			$(this).parents('form').append(popUpHtml);

			// いいえボタン押下時の設定
			$('.btn-cancel-previous').on('click', function(e) {
				e.preventDefault();
				$.magnificPopup.close();
			});

			// はいボタン押下時の設定
			$('.btn-yes-previous').on('click', function(e) {
				e.preventDefault();
				$.magnificPopup.close();

				overlay('on');
				thisEvent.parents('form').attr("action", thisEvent.attr("data-action")).submit();
				overlay('off');
			});

			$('#modal-previous').plainDialog('open', {
				overlay: {
					fillColor: '#000',
					opacity: 0.5
				}
			});
			overlay('off');

		} else {
			thisEvent.parents('form').attr("action", thisEvent.attr("data-action")).submit();

		}
		overlay('off');
	});

	var topButton = $('#top-page');
	var bottomButton = $('#bottom-page');
	topButton.hide();

	$(window).scroll(function() {
		if(($(document).height() - ($(this).height() + $(this).scrollTop())) < 100) {
			topButton.fadeIn();
			bottomButton.fadeOut();
		} else {
			topButton.fadeOut();
			bottomButton.fadeIn();
		}
	});

	$('#top-page').on('click', function(e) {
		e.preventDefault();
		return $('html, body').animate({
			scrollTop: 0
		}, 1000, "swing");
	});

	$('#bottom-page').on('click', function(e) {
		e.preventDefault();
		return $('html, body').animate({
			scrollTop: $(document).height()
		}, 1000, "swing");
	});

	$('#collapseExample1').on({
		'show.bs.collapse': function(e) {
			var targetId = e.target.id;
			if (targetId == "collapseExample1") {
				$('.collapse-accordion-btn').html('<i class="fa fa-chevron-up"></i>&nbsp;閉じる');
				$('#state-accordion').val("true");
			}
		},
		'hide.bs.collapse': function(e) {
			var targetId = e.target.id;
			if (targetId == "collapseExample1") {
				$('.collapse-accordion-btn').html('<i class="fa fa-chevron-down"></i>&nbsp;開く&nbsp;&nbsp;&nbsp;');
				$('#state-accordion').val("false");
			}
		}
	});
});

// 和暦日付プルダウンの初期化
function initJapaneseCalList(yearList, era_idname, year_idname, month_idname, day_idname, era_data, year_data, month_data, day_data) {
	$('#' + era_idname).val(era_data);
	var html_string = '<option value=""></option>';
	var era_string = $('#'+ era_idname).val();
	$.each(yearList, function(index, elem) {
		if(elem.era == era_string) {
			html_string = html_string + '<option value="' + elem.value + '">' + elem.key + '</option>';
		}
	});
	$('#' + year_idname).empty();
	$('#' + year_idname).html(html_string);
	$('#' + year_idname).val(year_data);
	$('#' + month_idname).val(month_data);
	$('#' + day_idname).val(day_data);
}

// datepickerの定義
function defineDatePicker(yearList, datepicker_idname, era_idname, year_idname, month_idname, day_idname) {
	$('#' + datepicker_idname).datepicker({
		format : 'yyyy年mm月dd日',
		todayBtn: true,
		autoclose: true,
		language : 'ja'
	}).on('changeDate', function(e) {
		var date = new Date();
		var dateObj = new dateJp();

		$('#' + era_idname).val(dateObj.dateFormatJp(e.date, "ggg"));
		var html_str = '<option value=""></option>';
		var era = $('#' + era_idname).val();
		$.each(yearList, function(index, elem) {
			if(elem.era == era) {
				html_str = html_str + '<option value="' + elem.value + '">' + elem.key + '</option>';
			}
		});
		$('#' + year_idname).empty();
		$('#' + year_idname).html(html_str);
		$('#' + year_idname).val(dateObj.dateFormatJp(e.date, "ee"));
		$('#' + month_idname).val(dateObj.dateFormatJp(e.date, "MM"));
		$('#' + day_idname).val(dateObj.dateFormatJp(e.date, "dd"));
	});
}

// 元号プルダウン変更時処理
function changeEraList(yearList, era_idname, year_idname, month_idname, day_idname) {
	$('#' + era_idname).on('change', function(e) {
		var html_str = '<option value=""></option>';
		var era = $(this).val();
		$.each(yearList, function(index, elem) {
			if(elem.era == era) {
				html_str = html_str + '<option value="' + elem.value + '">' + elem.key + '</option>';
			}
		});
		$('#' + year_idname).empty();
		$('#' + year_idname).html(html_str);
		$('#' + year_idname).val("");
		$('#' + month_idname).val("");
		$('#' + day_idname).val("");
	});
}

//ファイルアップロード部品
function defineFileUploadCompornentFunc() {
	$('.upload-selected-btn').on('click', function(e) {
		e.preventDefault();
		$(this).parent().parent().parent().parent().parent().find('.uploaded-file').trigger('click');
	});

	$('.uploaded-file').on('change', function(e) {
		var thisEvent = $(this);
		var formData = new FormData();

		var file = $(this).prop('files')[0];
		var uploadfile = thisEvent.get()[0].files;

		for (var i = 0; i < uploadfile.length; i++) {
			formData.append("uploadfile" , uploadfile[i]);
		};

		thisEvent.val('');

		$.ajax({
            url:  "fileupload!upload",
            type: "POST",
            async: true,
            xhr : function(){
                XHR = $.ajaxSettings.xhr();
                if(XHR.upload){

					XHR.upload.addEventListener('loadstart',function(e){

						if(thisEvent.parent().find('.file-upload-progress').hasClass('display-none')) {
							thisEvent.parent().find('.upload-selected-btn').prop('disabled', true);
							thisEvent.parent().find('.file-upload-progress').removeClass("display-none");
							thisEvent.parent().find('.uploaded-file-name-field').addClass("display-none");
						}
					});

					XHR.upload.addEventListener('progress',function(e){

                        var progre = parseInt(e.loaded / e.total * 10000 ) / 100;

						thisEvent.parent().find('.file-upload-progress-bar').css('width', progre + '%');
						thisEvent.parent().find('.file-upload-progress-bar').attr('aria-valuenow', progre);
						thisEvent.parent().find('.file-upload-progress-bar').text(progre + '%');
					});

					XHR.upload.addEventListener('load',function(e){
						thisEvent.parent().find('.uploaded-file-name').html('<i class="fa fa-upload"></i>&nbsp;' + file.name);
						thisEvent.parent().find('.hidden-temporarily-stored-filename').val(file.name);
						thisEvent.parent().find('.uploaded-file-name-field').removeClass("display-none");
						thisEvent.parent().find('.file-upload-progress').addClass("display-none");
						thisEvent.parent().find('.file-upload-progress-bar').css('width', '0%');
						thisEvent.parent().find('.file-upload-progress-bar').attr('aria-valuenow', '0');
						thisEvent.parent().find('.file-upload-progress-bar').text('0%');
						thisEvent.parent().find('.upload-selected-btn').prop('disabled', false);
					});

					XHR.upload.addEventListener('error',function(e){
					});
	            }
				return XHR;
			},
            data: formData,
            contentType: false,
            processData: false

        }).done(function( data ) {
			ajaxCommonDone(data, function(data) {
				if (data != undefined) {
					thisEvent.parent().find('.hidden-temporarily-stored_seq').val(data.wTemporarilyStoredSeq);
					thisEvent.parent().find('.hidden-temporarily-stored-filename').val(data.temporarilyStoredFileName);
				}
			});
			overlay('off');
        }).fail(function(data){
			ajaxCommonFail();
			overlay('off');
        });
	});
}

// 全画面オーバレイ
function overlay(mode) {
	if(mode == "on") {
		current_scrollY = $(window).scrollTop();
//		$('#main-content').css({
		$('body').css({
			position: 'fixed',
//			top: (-1 * current_scrollY) + 150 + 'px'
			top: (-1 * current_scrollY) + 'px'
		});
		var html_str = '<div id="loading-overlay">';
		html_str = html_str + '<div class="sk-cube-grid">';
		html_str = html_str + '<div class="sk-cube sk-cube1"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube2"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube3"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube4"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube5"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube6"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube7"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube8"></div>';
		html_str = html_str + '<div class="sk-cube sk-cube9"></div>';
		html_str = html_str + '</div>';
		html_str = html_str + '</div>';
		$('#container').after(html_str);
	} else {
//		$('#main-content').css({
		$('body').css({
//			position: 'block'
			position: ''
		});
		$('#loading-overlay').remove();
	}
}

// ログアウト確認ダイアログ画面
function printSignoutQDialog() {
	var html_str = '<div id="sign-out-dialog" class="plainmodal-dialog">';
	html_str = html_str + '<div class="dialog-close-box plaindialog-close"><i class="fa fa-close"></i></div>';
	html_str = html_str + '<div class="dialog-body">';
	html_str = html_str + '<div>';
	html_str = html_str + '<div class="question"></div>';
	html_str = html_str + '</div>';
	html_str = html_str + '<div class="title">ログアウトの確認</div>';
	html_str = html_str + '<div class="msg">';
	html_str = html_str + 'ログアウトします。よろしいですか？';
	html_str = html_str + '</div>';
	html_str = html_str + '</div>';
	html_str = html_str + '<div class="dialog-footer">';
	html_str = html_str + '<ul class="inline">';
	html_str = html_str + '<li><button type="button" class="btn bg-color" id="exit-close-btn">はい</button></li>';
	html_str = html_str + '<li><button type="button" class="btn plaindialog-close">いいえ</button></li>';
	html_str = html_str + '</ul>';
	html_str = html_str + '</div>';
	html_str = html_str + '</div>';
	$('#container').after(html_str);
}

// ブラウザ名の取得
var getBrowser = function() {
	var ua = window.navigator.userAgent.toLowerCase();
	var ver = window.navigator.appVersion.toLowerCase();
	var name = 'unknown';

	if(ua.indexOf("msie") != -1) {
		if(ver.indexOf("msie 6.") != -1) {
			name = 'ie6';
		} else if(ver.indexOf("msie 7.") != -1) {
			name = 'ie7';
		} else if(ver.indexOf("msie 8.") != -1) {
			name = 'ie8';
		} else if(ver.indexOf("msie 9.") != -1) {
			name = 'ie9';
		} else if(ver.indexOf("msie 10.") != -1) {
			name = 'ie10';
		} else {
			name = 'ie';
		}
	} else if(ua.indexOf('trident/7') != -1) {
		name = 'ie11';
	} else if(ua.indexOf('chrome') != -1) {
		name = 'chrome';
	} else if(ua.indexOf('safari') != -1) {
		name = 'safari';
	} else if(ua.indexOf('opera') != -1) {
		name = 'opera';
	} else if(ua.indexOf('firefox') != -1) {
		name = 'firefox';
	}
	return name;
};

function ajaxCommonDone(data, callback) {

	if (data != null && 'ajaxError' in data) {

		var $messageArea = $('<ul class="errorMessage"></ul>');
		for (var i = 0; i < data.ajaxError.length; i++) {
			$messageArea.append('<li><span>' + data.ajaxError[i] + '</span></li>');
		}
		$("#errorMessageArea").empty();
		$("#errorMessageArea").append($messageArea);
		$('#errorMessageArea').removeClass('display-none');
		overlay('off');

		location.href = "#top";

	} else {
		callback(data);
	}
}

function ajaxCommonFail() {

	var $messageArea = $('<ul class="errorMessage"></ul>');
	$messageArea.append('<li><span>システムエラーが発生しました。公益認定等事務支援システムログイン画面よりログインし再度操作をしてください。</span></li>');
	$("#errorMessageArea").empty();
	$("#errorMessageArea").append($messageArea);
	$('#errorMessageArea').removeClass('display-none');

	location.href = "#top";
}

function searchResultDisplay (searchCountUpperLimitExcessFlag, zeroCountFlag, searchMessage) {
	if (searchCountUpperLimitExcessFlag == "true") {
		console.log("searchCountUpperLimitExcessFlag");
		setTimeout(function() {
			var messages = searchMessage.split('\n');
			var html_message = '';
			var table_message = '';
			for (var i = 0; i < messages.length; i++) {
				if (i == 0) {
					html_message = html_message + messages[i];
				} else {
					html_message = html_message + '<br/>' + messages[i];
				}
				table_message = table_message + messages[i];
			}
			$('.msg').html(html_message);
			$('#table-msg-area').html(table_message);
			$('#warning-dialog').plainDialog('open', {
				overlay: {
					fillColor: '#000',
					opacity: 0.5
				}
			});
		}, 100);
	} else if (zeroCountFlag == "true") {
		console.log("zeroCountFlag");
		setTimeout(function() {
			var message = searchMessage
			var table_message = '';
			for (var i = 0; i < message.length; i++) {
				table_message = table_message + message[i];
			}
			$('#table-msg-area').html(table_message);
		}, 100);
	}


}
