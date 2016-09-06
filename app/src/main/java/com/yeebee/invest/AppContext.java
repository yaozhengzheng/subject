package com.yeebee.invest;

public class AppContext extends BaseApplication {

	private static AppContext instance;
	
	@Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
	}
	
	/**
     * 获得当前app运行的AppContext
     * 
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }
}
