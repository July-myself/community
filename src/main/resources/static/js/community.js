function postComment() {
    //获取评论的questionId
    var questionId = $("#question_id").val();
    //获取评论内容
    var content = $("#comment_content").val();
    console.log(questionId,content);
    console.log("准备");
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
        success:function (response,data) {
            console.log(data);
            if (response.code == 200){
                $("#comment_section").hide();
            }else {
                alert(response.message);
            }
            console.log(response);
        }

    });
    console.log("结束");

}