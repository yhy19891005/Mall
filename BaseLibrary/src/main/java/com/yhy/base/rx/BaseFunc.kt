package com.yhy.base.rx

import com.yhy.base.common.BaseConstant
import com.yhy.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFunc<T>: Func1<BaseResp<T>, Observable<T>> {

    override fun call(t: BaseResp<T>): Observable<T> {
        if (t.status != BaseConstant.RESP_SUC) {
          return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(t.data)
    }
}