function postComment() {
    //获取评论的questionId
    var questionId = $("#question_id").val();
    //获取评论内容
    var content = $("#comment_content").val();
    //添加非空判断
    if (!content){
        alert("回复内容不能为空");
        return;
    }
    //发出POST请求
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        dataType:"json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            if (response.code == 200){
                //回复成功后刷新页面以显示内容
                window.location.reload();
            }else {
                if(response.code == 3001){
                    //登录异常
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=728bc9dfda3dc246ba09&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        //实现不刷新页面的登录
                        window.localStorage.setItem("closable","true");
                    }

                }else {
                    alert(response.message);
                }
            }
        }

    });

}