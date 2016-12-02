# LubusDemo
一个用于模块间解耦的框架

LuBus in 3 steps: 

    1.Initialize the module
    LuBusManager.getInstance().init(Context context, String[] pathList);
    
    2.Make your class extend Lu
    make sure your uri be formatted like:module/method
    
    3.use the call
    LuBusManager.getInstance().call(Context context, "module/test", List<Object> paramList);
    


