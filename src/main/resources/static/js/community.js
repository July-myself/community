/**
 * 实现评论的功能
 */
function comment(e) {
    var type = e.getAttribute("data-type");//获取回复类型
    //根据回复类型的不同获取父id
    if (type == 1){
        //问题
        var parentId = $("#question_id").val();//获取questionId
        var content = $("#comment_content").val();//获取问题评论的内容
    }else if (type == 2){
        //子评论
        var parentId = e.getAttribute("data-id"); //获取父评论的id
        var content = $("#sub_commit").val();//获取子评论的内容
    }
    postComment(parentId,type,content);

}

/**
 * 发出post请求
 * @param parentId 评论父Id
 * @param type 评论父类型
 * @param content 评论内容
 */
function postComment(parentId,type,content) {
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
            "parentId":parentId,
            "content":content,
            "type":type
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



/**
 * 点击 展开/折叠二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id"); //获取点击评论的id
    var comments = $("#comment-"+id);//获取点击到的评论的子回复部分div
    var isCollapse = $("#comment-"+id).hasClass("in")//获取样式，以判断展开状态

    if (isCollapse){

        comments.removeClass("in");//移除样式，使其折叠
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+id);

        if (subCommentContainer.children().length == 1){
            //需要请求后台数据，然后再展开
            $.getJSON("/comment/"+id,function (subComments) {
                //追加标签以显示子评论数据

                $.each(subComments.data.reverse(),function (index,comment) {
                    //子评论动态构成
                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    });
                    var mediaElement = $("<div/>",{
                        "class":"media"
                    });
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "text":comment.user.name
                    })).append($("<div/>",{
                        "text":comment.content
                    })).append($("<div/>",{
                        "class":"comment-operate"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "text":moment(comment.timeModified).format('YYYY-MM-DD HH:mm')
                    })));

                    commentElement.append(mediaElement);
                    mediaElement.append(mediaLeftElement);
                    mediaElement.append(mediaBodyElement);
                    subCommentContainer.prepend(commentElement);

                });
            });

            }

            comments.addClass("in"); //添加css样式，使其展开
            e.classList.add("active");


    }
}

/**
 * 选取标签，标签追加写入输入框中
 * @param s 标签名
 */
function selectTag(e) {
    var tag = e.getAttribute("data-tag");
    var previous = $("#tag").val(); //获得原标签输入框中的内容
    if (previous.indexOf(tag) == -1){
        //不存在才添加
        if (previous){
            $("#tag").val(previous + ',' + tag); //追加
        }else{
            $("#tag").val(tag);
        }
    }
}

/**
 * 标签库的展示与隐藏
 */
function showSelectTags() {
    $("#select-tags").show();
}
