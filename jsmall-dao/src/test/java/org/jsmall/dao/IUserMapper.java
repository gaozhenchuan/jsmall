package org.jsmall.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface IUserMapper {

    /**
     * <dd>概要：問合せ情報を取得する。
     * <dd>詳細：問合せ情報を取得する。
     * <dd>備考：
     * @param  Fqc0001SearchDto 問合せ情報検索条件DTO
     * @return List<Fqb0001InquiryDto> 問合せ情報検索結果DTO
     */
    @Select({
            "select",
            "INQUIRY_DATETIME,",
            "PREF_CODE,",
            "PREF_NAME,",
            "AFFILIATED_POST,",
            "QUESTIONER,",
            "PLACE_TO_CONTACT,",
            "INQUIRY_TITLE,",
            "INQUIRY_CONTENTS,",
            "INQUIRY_STAT",
            "from INQUIRY_INFO ",
            "where INQUIRY_SEQ = #{inquirySeq,jdbcType=NUMERIC} ",
            "and DELETED_FLAG = '0' "
    })
    @Results({@Result(column = "INQUIRY_DATETIME", property = "inquiryDatetime", jdbcType = JdbcType.DATE),
            @Result(column = "PREF_CODE", property = "prefCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PREF_NAME", property = "prefName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "AFFILIATED_POST", property = "affiliatedPost", jdbcType = JdbcType.VARCHAR),
            @Result(column = "QUESTIONER", property = "questioner", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PLACE_TO_CONTACT", property = "placeToContact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "INQUIRY_TITLE", property = "inquiryTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "INQUIRY_CONTENTS", property = "inquiryContents", jdbcType = JdbcType.VARCHAR),
            @Result(column = "INQUIRY_STAT", property = "inquiryStat", jdbcType = JdbcType.VARCHAR)})
    void getInquiryInfo(int inquirySeq);
}
