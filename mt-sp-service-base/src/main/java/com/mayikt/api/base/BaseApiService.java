package com.mayikt.api.base;

import com.mayikt.api.constant.Constants;
import com.mayikt.api.utils.MeiteBeanUtils;
import lombok.Data;

/**
 * @Classname BaseApiService
 * @Description TODO
 * @Date 2021/3/10 16:57
 * @Created by li.yy
 */
@Data
public class BaseApiService<T> {

    public BaseResponse<T> setResultError(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    /**
     * 返回错误，可以传msg
     *
     * @param msg
     * @return
     */
    public BaseResponse<T> setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    /***
     * 返回成功，可以传data值
     * @param data
     * @return
     */
    public BaseResponse<T> setResultSuccess(T data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    /**
     * 返回成功，沒有data值
     *
     * @return
     */
    public BaseResponse<T> setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }


    /**
     * 通用封装 通用封装
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */

    public BaseResponse<T> setResult(Integer code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

    /**
     * dto 转换do
     *
     * @param dtoEntity
     * @param doClass
     * @param <Do>
     * @return
     */
    public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
        return MeiteBeanUtils.dtoToDo(dtoEntity, doClass);
    }

    /**
     * do转换成dto
     * @param doEntity
     * @param dtoClass
     * @param <Dto>
     * @return
     */
    public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
        return MeiteBeanUtils.doToDto(doEntity, dtoClass);
    }

    public BaseResponse<T> setResultDb(int dbCount, T successMsg, String errorMsg) {
        return dbCount > 0 ? setResultSuccess(successMsg) :
                setResultError(errorMsg);
    }
}
