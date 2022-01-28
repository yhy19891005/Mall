package com.yhy.base.common

object RouterPath {

    class UserCenter{
        companion object{
            const val PATH_LOGIN = "/UserCenter/Login"
        }
    }

    class OrderCenter{
        companion object{
            const val PATH_ORDER_CONFIRM = "/OrderCenter/Confirm"
        }
    }

    class PayCenter{
        companion object{
            const val PATH_CASH_REGISTER = "/PayCenter/CashRegister"
        }
    }

    class MessageCenter{
        companion object{
            const val PATH_MESSAGE_PUSH = "/MessageCenter/Push"
            const val PATH_MESSAGE_ORDER = "/MessageCenter/Order"
        }
    }
}