package org.jsmall.dao.master;

import java.util.List;

import org.jsmall.dao.master.entity.Code;

public interface CodeMstDao {

    List<Code> selectByCodeSetId(String key);

    int insert(Code paramCodeMst);

    int update(Code paramCodeMst);
    
    Code selectByPrimaryKey(String key);
}
