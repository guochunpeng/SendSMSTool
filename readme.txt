1.主工程build.gradle
compile 'com.umeng.analytics:analytics:latest.integration'
compile 'com.umeng.sdk:common:latest.integration'
compile 'com.umeng.sdk:analytics:latest.integration'
2.MyApplication
inityoumeng()方法  友盟相关key等配置信息保存在GlobalParams
3. 页面统计   BaseActivity 和BaseFragment中onResume 和onPause方法中super...方法之后调用 MobclickAgent.onResume(this); MobclickAgent.onPause(this);  所有页面继承于BaseActivity或BaseFragment

4.自定义事件统计
  1）在官网添加自定义事件
  2）MobclickAgent.onEvent(mContext,"Forward");  在NavigationActivity中点击button为一个点击事件demo

