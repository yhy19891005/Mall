package com.yhy.message.receiver

import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.*
import cn.jpush.android.service.JPushMessageReceiver
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.rx.Bus
import com.yhy.message.event.MessageBridgeEvent
import com.yhy.order.ui.activity.OrderDetailActivity
import org.json.JSONObject

class MessageReceiver: JPushMessageReceiver() {

    private val TAG = "PushMessageReceiver"

    override fun onMessage(context: Context, customMessage: CustomMessage) {
        Bus.send(MessageBridgeEvent(true))
        Log.e(TAG, "[onMessage] $customMessage")
        val intent = Intent("com.jiguang.demo.message")
        intent.putExtra("msg", customMessage.message)
        context.sendBroadcast(intent)
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageOpened] $message")
        val extras = message.notificationExtras
        val jsonObj = JSONObject(extras)
        val orderId = jsonObj.optInt("orderId")
        Log.e("onNotifyMessageOpened", "orderId = $orderId")

        val i = Intent(context, OrderDetailActivity::class.java)
        i.putExtra(ProviderConstant.KEY_ORDER_ID, orderId)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)

        //ARouter跳转无效?????
//        ARouter.getInstance()
//            .build(RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
//            .withInt(ProviderConstant.KEY_ORDER_ID, orderId)
//            .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            .navigation()

//        try {
//            //打开自定义的Activity
//            val i = Intent(context, TestActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle)
//            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent)
//            i.putExtras(bundle)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            context.startActivity(i)
//        } catch (throwable: Throwable) {
//        }
    }

    override fun onMultiActionClicked(context: Context?, intent: Intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮")
        val nActionExtra = intent.extras!!.getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA)

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null")
            return
        }
        if (nActionExtra == "my_extra1") {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一")
        } else if (nActionExtra == "my_extra2") {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二")
        } else if (nActionExtra == "my_extra3") {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三")
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义")
        }
    }

    override fun onNotifyMessageArrived(context: Context?, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageArrived] $message")
        Bus.send(MessageBridgeEvent(true))
/*
        try {
            context?.let {
                val intent = Intent(context, OrderDetailActivity::class.java)
                val extras = message.notificationExtras
                val jsonObj = JSONObject(extras)
                val orderId = jsonObj.optInt("orderId")
                intent.putExtra(ProviderConstant.KEY_ORDER_ID, orderId)
                val pendingIntent = PendingIntent.getBroadcast(it, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                val channelId: String = "developer-default"
                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(it, channelId)
                        .setContentTitle(message.notificationTitle)
                        .setSmallIcon(R.drawable.icon_shop)
                        .setContentText(message.notificationContent)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)

                val notificationManager =
                   it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Since android Oreo notification channel is needed.

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager.createNotificationChannel(channel)
                }

                notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build())
            }
        } catch (e: Exception) {
            Log.e(TAG, "[onNotifyMessageArrived] ${e.message}")
        }*/
    }

    override fun onNotifyMessageDismiss(context: Context?, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageDismiss] $message")
    }

    override fun onRegister(context: Context, registrationId: String) {
        Log.e(TAG, "[onRegister] $registrationId")
        val intent = Intent("com.jiguang.demo.register")
        context.sendBroadcast(intent)
    }

    override fun onConnected(context: Context?, isConnected: Boolean) {
        Log.e(TAG, "[onConnected] $isConnected")
    }

    override fun onCommandResult(context: Context?, cmdMessage: CmdMessage) {
        Log.e(TAG, "[onCommandResult] $cmdMessage")
    }

    override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage)
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage)
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage)
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage)
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }

    override fun onNotificationSettingsCheck(context: Context?, isOn: Boolean, source: Int) {
        super.onNotificationSettingsCheck(context, isOn, source)
        Log.e(TAG, "[onNotificationSettingsCheck] isOn:$isOn,source:$source")
    }
}