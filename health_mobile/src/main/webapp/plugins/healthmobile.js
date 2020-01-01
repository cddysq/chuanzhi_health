//获取指定的URL参数值 http://localhost/pages/setmeal_detail.html?id=12&name=jack
function getUrlParam(paraName) { //传入id
    const url = document.location.toString(); //获取地址栏连接
    const temp = url.split("?"); //通过？进行拆分，得到请求参数 例：[http://localhost/pages/setmeal_detail.html,id=12&name=jack]
    if (temp.length > 1) {
        //有参
        const parameters = temp[1].split("&"); //取到参数，按照&进行再次拆分 例：[id=12,name=jack]
        let param;
        for (let i of parameters) { //遍历参数数组 i=[id=12] i=[name=jack]
            param = i.split("="); //元素通过=进行拆分 [id,12]
            if (param != null && param[0] === paraName) { //进行判断是否与想得到的参数名相同 id=id
                return param[1]; //返回相应参数=后面的值 12
            }
        }
        //不满足返回空
        return '';
    } else {
        //不满足返回空
        return '';
    }
}

//获得当前日期，返回字符串
function getToday() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth() + 1;//0表示1月，1表示2月
    var day = today.getDate();
    return (year + "-" + month + "-" + day);
}

//获得指定日期后指定天数的日期
function getSpecifiedDate(date, days) {
    date.setDate(date.getDate() + days);//获取指定天之后的日期
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return (year + "-" + month + "-" + day);
}

/**
 * 手机号校验
 1--以1为开头；
 2--第二位可为3,4,5,7,8,中的任意一位；
 3--最后以0-9的9个整数结尾。
 */
function checkTelephone(telephone) {
    const reg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if (!reg.test(telephone)) {
        return false;
    } else {
        return true;
    }
}

/**
 * 身份证号码校验
 * 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
 */
function checkIdCard(idCard) {
    const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return !!reg.test(idCard);
}

let clock = '';//定时器对象，用于页面30秒倒计时效果
let nums = 60;
let validateCodeButton;

//基于定时器实现30秒倒计时效果
function doLoop() {
    validateCodeButton.disabled = true;//将按钮置为不可点击
    nums--;
    if (nums > 0) {
        validateCodeButton.value = nums + '秒后重新获取';
    } else {
        clearInterval(clock); //清除js定时器
        validateCodeButton.disabled = false;
        validateCodeButton.value = '重新获取验证码';
        nums = 30; //重置时间
    }
}