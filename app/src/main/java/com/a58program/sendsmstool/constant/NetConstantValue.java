package com.a58program.sendsmstool.debitapplication.constant;


/**
 * Created by zhangchi on 2016/6/14.
 */
public class NetConstantValue {
    // release
    public static String COMMONURI = "https://hy.9maibei.com/";

    public static final String tempUrl="HY_Mall/public";
//    public static String COMMONURI = "http://dev2api.9maibei.com/";

    public static String appInitUrl(){
        return COMMONURI+"Home/System/appInit";
    }
    public static boolean getService() {
        if (COMMONURI.equals("https://api.9maibei.com/")) {
            return true;
        }
        return false;
    }


    public static String getConfigUrl(){
        return COMMONURI+"Home/System/getShowConfig";
    }
    public static String getLoanStrategyUrl(){
        return COMMONURI+tempUrl+"/home/loanStrategy_hy.html";
    }
    /**
     * 获取验证码
     * api: com.maibei.merchants.net.api.GetVerifyCode.getVerifyCode()
     *
     * @return
     */
    public static String getVerifyCodeUrl() {
        return COMMONURI+"Home/Customer/getVerifyCode";
    }

    /**
     * 保存用户身份证信息
     * api: com.maibei.merchants.net.api.SaveIdCardInformation.saveIdCardInformation()
     *
     * @return
     */
    public static String getSaveIdCardInformationUrl() {
        return COMMONURI+"Home/Customer/saveIdCardInformation";
    }

    public static String getAuthList(){
        return  COMMONURI+"Home/MallHome/getAuthList";
    }
    /**
     * 获取已经绑定的银行卡列表
     * api: com.maibei.merchants.net.api.GetBankList.getBankList()
     *
     * @return
     */
    public static String getBankListUrl() {
        return COMMONURI+"Home/UserCenter/getBankList";
    }

    /**
     * 批量保存联系人
     * api: com.maibei.merchants.net.api.SaveContacts.saveContacts()
     *
     * @return
     */
    public static String getSaveContactsUrl() {
        return COMMONURI+"Home/System/saveContacts";
    }

    /**
     * 批量保存通话记录 (支持增量，即只传上次上行数据之后的通话记录)
     * api: com.maibei.merchants.net.api.SaveCallRecord.saveCallRecord()
     *
     * @return
     */
    public static String getSaveCallRecordUrl() {
        return COMMONURI+"Home/System/saveCallRecord";
    }


    /*
    * 批量保存短信
    * */
    public static String getSaveSMSMessageUrl(){
        return COMMONURI+"Home/System/saveSMSMessage";
    }


    /*
    * 批量保存用户应用列表
    * */

    public static String getSaveApplicationsUrl(){
        return COMMONURI+"Home/System/saveApplication";
    }
    /**
     * 取上一次保存通话记录的时间，下次增量上传通话记录的开始时间
     * api: com.maibei.merchants.net.api.SetLastSaveCallRecordTime.getLastSaveCallRecordTime()
     *
     * @return
     */
    public static String getLastSaveCallRecordTimeUrl() {
        return COMMONURI+"Home/System/getLastSaveCallRecordTime";
    }


    /**
     * 还款，立即还款
     *
     * @return
     */
    public static String getRepaymentUrl() {
        return COMMONURI+"Home/Pay/payConfirm";
    }

    /**
     * 检查版本更新
     * api: com.maibei.merchants.net.api.CheckUpgrade.checkUpgrade()
     *
     * @return
     */
    public static String GetCheckUpgradeUrl() {
        return COMMONURI+"Home/System/checkUpgrade";
    }


    /**
     * 图片上传接口
     * api: com.maibei.merchants.net.api.UploadImage.uploadImage()
     *
     * @return
     */
    public static String GetUploadImageUrl() {
        return COMMONURI+"Home/UserCenter/uploadImage";
    }

    /**
     * 重新设置密码
     * api: com.maibei.merchants.net.api.ResetPassword.resetPassword()
     *
     * @return
     */
    public static String getResetPasswordUrl() {
        return COMMONURI+"Home/Customer/resetPassword";
    }


    /**
     * 获取打开权限提示语
     * api: com.maibei.merchants.net.api.GetOpenPermissionHint.getOpenPermissionHint()
     *
     * @return
     */
    public static String getOpenPermissionHintUrl() {
        return COMMONURI+"Home/Customer/getOpenPermissionHint";
    }

    /**
     * 退出登录
     * api: com.maibei.merchants.net.api.Logout.logout()
     *
     * @return
     */
    public static String getLogoutUrl() {
        return COMMONURI+"Home/UserCenter/LogoutV2";
    }

    /**
     * 登录
     * api: com.maibei.merchants.net.api.Login.Login()
     *
     * @return
     */
    public static String getLoginUrl() {
        return COMMONURI+"Home/UserCenter/LogInV2";
    }

    /**
     * 绑定银行卡时获取验证码
     * api: com.maibei.merchants.net.api.GetBindVerifySms.getBindVerifySms()
     *
     * @return
     */
    public static String getBindVerifySmsUrl() {
        return COMMONURI+"Home/Pay/bindVerifySms";
    }

    /**
     * 绑定银行卡
     * api: com.maibei.merchants.net.api.BindBankCard.bindBankCard()
     *
     * @return
     */
    public static String getBindConfirmUrl() {
        return COMMONURI+"Home/Pay/bindConfirm";
    }

    /**
     * 获取可绑定卡的银行列表
     * api: com.maibei.merchants.net.api.GetAllBankList.getAllBankList()
     *
     * @return
     */
    public static String GetAllBankListUrl() {
        return COMMONURI+"Home/UserCenter/getAllBankList";
    }


    /**
     * 用户注册
     * api: com.maibei.merchants.net.api.GetSignUp.getSignUp()
     *
     * @return
     */
    public static String getSignUpUrl() {
        return COMMONURI+"Home/UserCenter/signUpV2";
    }

    /**
     * 活体检测成功通知
     * api: com.maibei.merchants.net.api.CreditFace.creditFace()
     *
     * @return
     */
    public static String getCreditFaceUrl() {
        return COMMONURI+"Home/Credit/creditFace";
    }

    /*
    * 微信支付下单
    * api:com.maibei.user.net.api.GetWeChatOrder.getWeChatOrder()
    * */
    public static String getWeChatOrderURl() {
        return COMMONURI+"Home/Pay/wxpayConfirm";
    }


    /*
    * 获取联系人信息
    *api:com.maibei.user.net.api.GetContactsInfo.getContactsInfo()
    * */
    public static String getGetContactsInfoURL() {
        return COMMONURI+"Home/Credit/getContactsInfo";
    }

    /*
    * 修改联系人信息
    *api:com.maibei.user.net.api.ChangeContactsInfo.changeContactsInfo()
    * */
    public static String getChangeContactsInfoURL() {
        return COMMONURI+"Home/Credit/changeContactsInfo";
    }

    /*
    * 获取提现账单
    *api:com.maibei.user.net.api.GetWithdrawalsBill.getWithdrawalsBill()
    * */
    public static String getGetWithdrawalsBillURL() {
        return COMMONURI+"Home/UserCenter/getWithdrawalsBill";
    }

    /*
    * 现金贷申请
    *api:com.maibei.user.net.api.WithdrawalsApply.withdrawalsApply()
    * */
    public static String getWithdrawalsApplyURL() {
        return COMMONURI+"Home/WithdrawalsOrder/withdrawalsApply";
    }

    /*
    * 提现待审核刷新
    *api:com.maibei.user.net.api.WithdrawalsRefresh.withdrawalsRefresh()
    * */
    public static String getWithdrawalsRefreshURL() {
        return COMMONURI+"Home/WithdrawalsOrder/withdrawalsRefresh";
    }

    /*
    * 现金贷提现下单
    *api:com.maibei.user.net.api.WithdrawalsOrder.withdrawalsOrder()
    * */
    public static String getWithdrawalsOrderURL() {
        return COMMONURI+"Home/WithdrawalsOrder/withdrawalsOrder";
    }

    /*
    * 获取提现账单详情
    *api:com.maibei.user.net.api.GetWithdrawalsBillInfo.getWithdrawalsBillInfo()
    * */
    public static String getWithdrawalsBillInfoURL() {
        return COMMONURI+"Home/UserCenter/getWithdrawalsBillInfo";
    }

    /*
    * 获取取现记录
    *api:com.maibei.user.net.api.GetWithdrawalsRecord.getWithdrawalsRecord()
    * */
    public static String getWithdrawalsRecordURL() {
        return COMMONURI+"Home/UserCenter/getWithdrawalsRecord";
    }


    /*
    * 获取消费账单
    *api:com.maibei.user.net.api.GetConsumeBigBillList.getConsumeBigBillList()
    * */
    public static String getConsumeBigBillListURL() {
        return COMMONURI+"Home/UserCenter/getConsumeBigBillList";
    }

    /*
    * 获取消费账单（小单）
    *api:com.maibei.user.net.api.GetConsumeSmallBillList.getConsumeSmallBillList()
    * */
    public static String getConsumeSmallBillListURL() {
        return COMMONURI+"Home/UserCenter/getConsumeSmallBillList";
    }

    /*
    * 日志上传接口
    * api:com.maibei.merchants.net.api.UploadLog.uploadLog()
    * */
    public static String getUploadLogUrl() {
        return COMMONURI+"Home/UserCenter/uploadUserLog";
    }

    /**
     * 获取城市（city）信息
     * api: com.maibei.merchants.net.api.GetCity.getCity()
     *
     * @return
     */
    public static String GetCityUrl() {
        return COMMONURI+"Home/Merchant/getCity";
    }

    /**
     * 获取州县（county）信息
     * api: com.maibei.merchants.net.api.GetCounty.getCounty()
     *
     * @return
     */
   public static String getHelpListUrl() {
        return COMMONURI+"Home/UserCenter/getHelpList";
    }


    public static String SelfInfoUrl() {
        return COMMONURI+"Home/Credit/getSelfInfo";
    }

    public static String getSaveSelfInfoUrl() {
        return COMMONURI+"Home/Credit/saveSelfInfo";
    }


    public static String getHomeData() {
        return COMMONURI+"Home/MallHome/index";
    }



    public static String loginPage(){
        return COMMONURI+tempUrl+"/user/add_phone.html";
    }


    public static String detailPageUrl(){
        return COMMONURI+tempUrl+"/product/detail.html";
    }

    public static String authListPage(){
       return COMMONURI+tempUrl+"/pay/authList.html";
    }

    public static String orderDetailPage(){
       return COMMONURI+tempUrl+"/pay/order_detail.html";
    }


    public static String noBillPageUrl(){
        return COMMONURI+tempUrl+"/tools/no_bills.html";
    }

    public static String mineOrderPageUrl(){
        return COMMONURI+tempUrl+"/user/mine_orders.html";
    }

    public static String authListPageUrl(){
        return COMMONURI+tempUrl+"/pay/authList.html";
    }

    public static String billDetailPageUrl(){
        return COMMONURI+tempUrl+"/pay/bill_pay.html";
    }

    public static String changePasswordPage(){
        return COMMONURI+tempUrl+"/user/forget_password01.html";
    }

    public static String myBankPage() {
        return COMMONURI+tempUrl+"/user/bank_list.html";
    }
}