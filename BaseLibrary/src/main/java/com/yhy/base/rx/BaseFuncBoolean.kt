package com.yhy.base.rx

import com.yhy.base.common.BaseConstant
import com.yhy.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFuncBoolean<T>: Func1<BaseResp<T>, Observable<Boolean>> {

    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status != BaseConstant.RESP_SUC) {
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(true)
    }
}