package org.jsmall.service.common.excel.model;

import java.io.Serializable;
import java.util.List;

public class ExcelBookModel implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -7899958545766398258L;

    private List<String> colsHeader;
    
    private List<String> rowsHeader;
    
    private String colsFontSize; 
    
    private List<String> sheetNames;
    
    private String workBookName;
}
