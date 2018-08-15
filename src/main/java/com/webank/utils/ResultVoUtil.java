package com.webank.utils;

import com.webank.vo.ResultVo;

public class ResultVoUtil {
    public static ResultVo success(Object object) {
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success() {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
