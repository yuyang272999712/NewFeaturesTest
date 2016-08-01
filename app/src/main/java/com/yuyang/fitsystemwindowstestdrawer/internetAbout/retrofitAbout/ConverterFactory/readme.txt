FileConverterFactory.java用于对Retrofit的请求／返回参数做处理
具体用法如下：

Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("url")
        .addConverterFactory(FileConverterFactory.create())
        .build();