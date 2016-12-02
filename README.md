# LubusDemo
一个用于模块间解耦的框架

LuBus in 3 steps: 
  First you should import LuBus into your project as a module then you can use it to decoupling your project

    1.Initialize the module
    LuBusManager.getInstance().init(Context context, String[] pathList);
    
    2.Make your class extend Lu
    make sure your uri be formatted like:module/method
    
    3.use the call
    LuBusManager.getInstance().call(Context context, "module/test", List<Object> paramList);
    


