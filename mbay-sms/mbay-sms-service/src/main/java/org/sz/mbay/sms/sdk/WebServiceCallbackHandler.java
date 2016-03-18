/**
 * WebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package org.sz.mbay.sms.sdk;

/**
 * WebServiceCallbackHandler Callback class, Users can extend this class and
 * implement their own receiveResult and receiveError methods.
 */
public abstract class WebServiceCallbackHandler {

    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the
     * NonBlocking Web service call is finished and appropriate method of this
     * CallBack is called.
     * 
     * @param clientData
     *            Object mechanism by which the user can pass in user data that
     *            will be avilable at the time this callback is called.
     */
    public WebServiceCallbackHandler(Object clientData) {
	this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public WebServiceCallbackHandler() {
	this.clientData = null;
    }

    /**
     * Get the client data
     */

    public Object getClientData() {
	return clientData;
    }

    /**
     * auto generated Axis2 call back method for balance method override this
     * method for handling normal response from balance operation
     */
    public void receiveResultbalance(
	    org.sz.mbay.sms.sdk.WebServiceStub.BalanceResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from balance operation
     */
    public void receiveErrorbalance(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllInfo2 method override
     * this method for handling normal response from getAllInfo2 operation
     */
    public void receiveResultgetAllInfo2(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetAllInfo2Response result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getAllInfo2 operation
     */
    public void receiveErrorgetAllInfo2(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sMSTest method override this
     * method for handling normal response from sMSTest operation
     */
    public void receiveResultsMSTest(
	    org.sz.mbay.sms.sdk.WebServiceStub.SMSTestResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from sMSTest operation
     */
    public void receiveErrorsMSTest(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mmsFileMT method override this
     * method for handling normal response from mmsFileMT operation
     */
    public void receiveResultmmsFileMT(
	    org.sz.mbay.sms.sdk.WebServiceStub.MmsFileMTResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mmsFileMT operation
     */
    public void receiveErrormmsFileMT(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for msgid method override this
     * method for handling normal response from msgid operation
     */
    public void receiveResultmsgid(
	    org.sz.mbay.sms.sdk.WebServiceStub.MsgidResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from msgid operation
     */
    public void receiveErrormsgid(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendSMS_R method override this
     * method for handling normal response from sendSMS_R operation
     */
    public void receiveResultsendSMS_R(
	    org.sz.mbay.sms.sdk.WebServiceStub.SendSMS_RResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from sendSMS_R operation
     */
    public void receiveErrorsendSMS_R(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdMmsSend method override this
     * method for handling normal response from mdMmsSend operation
     */
    public void receiveResultmdMmsSend(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdMmsSendResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdMmsSend operation
     */
    public void receiveErrormdMmsSend(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getFlag method override this
     * method for handling normal response from getFlag operation
     */
    public void receiveResultgetFlag(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetFlagResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getFlag operation
     */
    public void receiveErrorgetFlag(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for rECSMS_UTF8 method override
     * this method for handling normal response from rECSMS_UTF8 operation
     */
    public void receiveResultrECSMS_UTF8(
	    org.sz.mbay.sms.sdk.WebServiceStub.RECSMS_UTF8Response result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from rECSMS_UTF8 operation
     */
    public void receiveErrorrECSMS_UTF8(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdSmsSend_DES method override
     * this method for handling normal response from mdSmsSend_DES operation
     */
    public void receiveResultmdSmsSend_DES(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdSmsSend_DESResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdSmsSend_DES operation
     */
    public void receiveErrormdSmsSend_DES(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllInfo method override this
     * method for handling normal response from getAllInfo operation
     */
    public void receiveResultgetAllInfo(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetAllInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getAllInfo operation
     */
    public void receiveErrorgetAllInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getGaoDuan method override this
     * method for handling normal response from getGaoDuan operation
     */
    public void receiveResultgetGaoDuan(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetGaoDuanResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getGaoDuan operation
     */
    public void receiveErrorgetGaoDuan(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdAudioSend method override
     * this method for handling normal response from mdAudioSend operation
     */
    public void receiveResultmdAudioSend(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdAudioSendResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdAudioSend operation
     */
    public void receiveErrormdAudioSend(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for report2DES method override this
     * method for handling normal response from report2DES operation
     */
    public void receiveResultreport2DES(
	    org.sz.mbay.sms.sdk.WebServiceStub.Report2DESResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from report2DES operation
     */
    public void receiveErrorreport2DES(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdSmsSend_g method override
     * this method for handling normal response from mdSmsSend_g operation
     */
    public void receiveResultmdSmsSend_g(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdSmsSend_gResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdSmsSend_g operation
     */
    public void receiveErrormdSmsSend_g(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdFaxSend method override this
     * method for handling normal response from mdFaxSend operation
     */
    public void receiveResultmdFaxSend(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdFaxSendResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdFaxSend operation
     */
    public void receiveErrormdFaxSend(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for rECSMSEx_UTF8 method override
     * this method for handling normal response from rECSMSEx_UTF8 operation
     */
    public void receiveResultrECSMSEx_UTF8(
	    org.sz.mbay.sms.sdk.WebServiceStub.RECSMSEx_UTF8Response result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from rECSMSEx_UTF8 operation
     */
    public void receiveErrorrECSMSEx_UTF8(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for report method override this
     * method for handling normal response from report operation
     */
    public void receiveResultreport(
	    org.sz.mbay.sms.sdk.WebServiceStub.ReportResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from report operation
     */
    public void receiveErrorreport(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdMmsSend_ex method override
     * this method for handling normal response from mdMmsSend_ex operation
     */
    public void receiveResultmdMmsSend_ex(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdMmsSend_exResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdMmsSend_ex operation
     */
    public void receiveErrormdMmsSend_ex(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for setGaoDuan method override this
     * method for handling normal response from setGaoDuan operation
     */
    public void receiveResultsetGaoDuan(
	    org.sz.mbay.sms.sdk.WebServiceStub.SetGaoDuanResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from setGaoDuan operation
     */
    public void receiveErrorsetGaoDuan(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdSmsSend method override this
     * method for handling normal response from mdSmsSend operation
     */
    public void receiveResultmdSmsSend(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdSmsSendResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdSmsSend operation
     */
    public void receiveErrormdSmsSend(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for uDPSIGNEX method override this
     * method for handling normal response from uDPSIGNEX operation
     */
    public void receiveResultuDPSIGNEX(
	    org.sz.mbay.sms.sdk.WebServiceStub.UDPSIGNEXResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from uDPSIGNEX operation
     */
    public void receiveErroruDPSIGNEX(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for uDPPwd method override this
     * method for handling normal response from uDPPwd operation
     */
    public void receiveResultuDPPwd(
	    org.sz.mbay.sms.sdk.WebServiceStub.UDPPwdResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from uDPPwd operation
     */
    public void receiveErroruDPPwd(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdSmsSend_AES method override
     * this method for handling normal response from mdSmsSend_AES operation
     */
    public void receiveResultmdSmsSend_AES(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdSmsSend_AESResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdSmsSend_AES operation
     */
    public void receiveErrormdSmsSend_AES(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for gxmt method override this
     * method for handling normal response from gxmt operation
     */
    public void receiveResultgxmt(
	    org.sz.mbay.sms.sdk.WebServiceStub.GxmtResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from gxmt operation
     */
    public void receiveErrorgxmt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendSMSEx_R method override
     * this method for handling normal response from sendSMSEx_R operation
     */
    public void receiveResultsendSMSEx_R(
	    org.sz.mbay.sms.sdk.WebServiceStub.SendSMSEx_RResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from sendSMSEx_R operation
     */
    public void receiveErrorsendSMSEx_R(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for bianliang method override this
     * method for handling normal response from bianliang operation
     */
    public void receiveResultbianliang(
	    org.sz.mbay.sms.sdk.WebServiceStub.BianliangResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from bianliang operation
     */
    public void receiveErrorbianliang(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mongateCsGetSmsExEx method
     * override this method for handling normal response from
     * mongateCsGetSmsExEx operation
     */
    public void receiveResultmongateCsGetSmsExEx(
	    org.sz.mbay.sms.sdk.WebServiceStub.MongateCsGetSmsExExResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mongateCsGetSmsExEx operation
     */
    public void receiveErrormongateCsGetSmsExEx(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for register method override this
     * method for handling normal response from register operation
     */
    public void receiveResultregister(
	    org.sz.mbay.sms.sdk.WebServiceStub.RegisterResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from register operation
     */
    public void receiveErrorregister(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdMmsReceive method override
     * this method for handling normal response from mdMmsReceive operation
     */
    public void receiveResultmdMmsReceive(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdMmsReceiveResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdMmsReceive operation
     */
    public void receiveErrormdMmsReceive(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for rECSMS method override this
     * method for handling normal response from rECSMS operation
     */
    public void receiveResultrECSMS(
	    org.sz.mbay.sms.sdk.WebServiceStub.RECSMSResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from rECSMS operation
     */
    public void receiveErrorrECSMS(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mt method override this method
     * for handling normal response from mt operation
     */
    public void receiveResultmt(org.sz.mbay.sms.sdk.WebServiceStub.MtResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mt operation
     */
    public void receiveErrormt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendSMS method override this
     * method for handling normal response from sendSMS operation
     */
    public void receiveResultsendSMS(
	    org.sz.mbay.sms.sdk.WebServiceStub.SendSMSResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from sendSMS operation
     */
    public void receiveErrorsendSMS(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mo2 method override this method
     * for handling normal response from mo2 operation
     */
    public void receiveResultmo2(
	    org.sz.mbay.sms.sdk.WebServiceStub.Mo2Response result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mo2 operation
     */
    public void receiveErrormo2(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getCode method override this
     * method for handling normal response from getCode operation
     */
    public void receiveResultgetCode(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetCodeResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getCode operation
     */
    public void receiveErrorgetCode(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdMmsSend_uex method override
     * this method for handling normal response from mdMmsSend_uex operation
     */
    public void receiveResultmdMmsSend_uex(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdMmsSend_uexResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdMmsSend_uex operation
     */
    public void receiveErrormdMmsSend_uex(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBalance method override this
     * method for handling normal response from getBalance operation
     */
    public void receiveResultgetBalance(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetBalanceResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getBalance operation
     */
    public void receiveErrorgetBalance(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdMmsSendF method override this
     * method for handling normal response from mdMmsSendF operation
     */
    public void receiveResultmdMmsSendF(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdMmsSendFResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdMmsSendF operation
     */
    public void receiveErrormdMmsSendF(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for chargUp method override this
     * method for handling normal response from chargUp operation
     */
    public void receiveResultchargUp(
	    org.sz.mbay.sms.sdk.WebServiceStub.ChargUpResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from chargUp operation
     */
    public void receiveErrorchargUp(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fileMT method override this
     * method for handling normal response from fileMT operation
     */
    public void receiveResultfileMT(
	    org.sz.mbay.sms.sdk.WebServiceStub.FileMTResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from fileMT operation
     */
    public void receiveErrorfileMT(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mo method override this method
     * for handling normal response from mo operation
     */
    public void receiveResultmo(org.sz.mbay.sms.sdk.WebServiceStub.MoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mo operation
     */
    public void receiveErrormo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for rECSMSEx method override this
     * method for handling normal response from rECSMSEx operation
     */
    public void receiveResultrECSMSEx(
	    org.sz.mbay.sms.sdk.WebServiceStub.RECSMSExResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from rECSMSEx operation
     */
    public void receiveErrorrECSMSEx(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for testCode method override this
     * method for handling normal response from testCode operation
     */
    public void receiveResulttestCode(
	    org.sz.mbay.sms.sdk.WebServiceStub.TestCodeResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from testCode operation
     */
    public void receiveErrortestCode(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mongateCsSpSendSmsNew method
     * override this method for handling normal response from
     * mongateCsSpSendSmsNew operation
     */
    public void receiveResultmongateCsSpSendSmsNew(
	    org.sz.mbay.sms.sdk.WebServiceStub.MongateCsSpSendSmsNewResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mongateCsSpSendSmsNew operation
     */
    public void receiveErrormongateCsSpSendSmsNew(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mdSmsSend_u method override
     * this method for handling normal response from mdSmsSend_u operation
     */
    public void receiveResultmdSmsSend_u(
	    org.sz.mbay.sms.sdk.WebServiceStub.MdSmsSend_uResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mdSmsSend_u operation
     */
    public void receiveErrormdSmsSend_u(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getStatus method override this
     * method for handling normal response from getStatus operation
     */
    public void receiveResultgetStatus(
	    org.sz.mbay.sms.sdk.WebServiceStub.GetStatusResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from getStatus operation
     */
    public void receiveErrorgetStatus(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mongateCsGetStatusReportExEx
     * method override this method for handling normal response from
     * mongateCsGetStatusReportExEx operation
     */
    public void receiveResultmongateCsGetStatusReportExEx(
	    org.sz.mbay.sms.sdk.WebServiceStub.MongateCsGetStatusReportExExResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from mongateCsGetStatusReportExEx operation
     */
    public void receiveErrormongateCsGetStatusReportExEx(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for uDPSIGN method override this
     * method for handling normal response from uDPSIGN operation
     */
    public void receiveResultuDPSIGN(
	    org.sz.mbay.sms.sdk.WebServiceStub.UDPSIGNResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from uDPSIGN operation
     */
    public void receiveErroruDPSIGN(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for unRegister method override this
     * method for handling normal response from unRegister operation
     */
    public void receiveResultunRegister(
	    org.sz.mbay.sms.sdk.WebServiceStub.UnRegisterResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from unRegister operation
     */
    public void receiveErrorunRegister(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sendSMSEx method override this
     * method for handling normal response from sendSMSEx operation
     */
    public void receiveResultsendSMSEx(
	    org.sz.mbay.sms.sdk.WebServiceStub.SendSMSExResponse result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from sendSMSEx operation
     */
    public void receiveErrorsendSMSEx(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for report2 method override this
     * method for handling normal response from report2 operation
     */
    public void receiveResultreport2(
	    org.sz.mbay.sms.sdk.WebServiceStub.Report2Response result) {
    }

    /**
     * auto generated Axis2 Error handler override this method for handling
     * error response from report2 operation
     */
    public void receiveErrorreport2(java.lang.Exception e) {
    }

}
