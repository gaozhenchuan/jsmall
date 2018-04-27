package org.jsmall.service.common.excel.model;

import java.io.Serializable;
import java.util.List;

public class ExcelSheetModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7899958545766398258L;

    private List<String> colsHeader;

    private List<String> rowsHeader;

    private String colsHeaderFontSize;

    private String colsHeaderFontCorlor;

    private String colsHeaderFont;

    private String colsHeaderAlignment;

    private String colsHeaderBackground;
    
    private String rowsHeaderFontSize;

    private String rowsHeaderFontCorlor;

    private String rowsHeaderFont;

    private String rowsHeaderAlignment;

    private String rowsHeaderBackground;

    private String sheetName;
    
    private String workBookName;
}
