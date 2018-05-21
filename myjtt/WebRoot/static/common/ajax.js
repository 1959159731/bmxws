/*
* url               发送请求的地址
* data              发送请求的参数
* callBack          请求的回调函数
* isHideLoading     Boolean，是否显示loading动画，此参数可传入也可不传入，
*                   当不传入时，isHideLoading会被后续传入的参数覆盖（很可能会被赋值成一
*                   个函数），此时可以当他是在给 callBack 赋值， 而忽略 isHideLoading，
*                   也就不会显示loading动画
* dataType          返回JSON数据
*/
function ajaxPost(url, data, isHideLoading, callBack, dataType) {
    var dataType;
    //如果没有显示loading动画，则此时isHideLoading会被赋值成一个函数，而如果第四个参数也没有传，第四个参数就是undefined
    if (!$.isFunction(callBack)) {
        if (callBack == undefined) {
            dataType = "json";
        } else {
            dataType = callBack;
        }
    } else {
        dataType = dataType || "json";
    }

    if (isHideLoading) {
        if ($.isFunction(isHideLoading)) {
            callBack = isHideLoading;
            isHideLoading = false;
        }
    }

    //可以根据自己的实际需求来定制是否显示loading动画
    if (isHideLoading) {
        loading();
    }
    
    $.post(url, data, function (res) {
        if (!isHideLoading) {
            $(".ajaxLoading").hide();
        }

        if (res != null && res != "") {
            if (callBack) {
                if ($.isFunction(callBack)) {
                    callBack(res);
                } else {
                    console.log("callBack is not a function");
                }                
            }            
        }
    }, dataType);
}