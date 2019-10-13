/**
 * 提交回复
 */
function postComment() {
    var questionId = $("#questionId").val();
    var content = $("#comment").val();
    postCommentToTarget(questionId, 1, content);
}

/**
 * 发送请求获取目标评论记录
 * @param targetId
 * @param type
 * @param content
 */
function postCommentToTarget(targetId, type, content) {
    if (!content) {
        alert("想回复可不能啥话都不说吖(⊙o⊙)~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    if (confirm(response.message)) {
                        window.open("https://github.com/login/oauth/authorize?client_id=6b94612764d44e4ce138&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    postCommentToTarget(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");

    // 设置二级评论折叠状态
    $("#comment-" + id).collapse('toggle');
    if (e.classList.contains("active")) {
        e.classList.remove("active");
    } else {
        $.getJSON( "/comment/" + id, function( data ) {
            var commentBodyId = $("#comment-body-" + id);
            var items = [];
            commentBodyId.append(
                $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                    "id": "comment-id-" + id,
                    html: items.join("")
                })
            ),
            $.each(data.data, function(comment) {
                var c = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 sub-comments",
                    html: comment.content
                });
                items.push(c);

            });

        });
        e.classList.add("active");
        console.info(id);
    }
}

/**
 * 取消回复二级评论
 * @param e
 */
function collapseSecondComments(e) {
    $("#comment-" + e.getAttribute("data-id")).collapse("hide");
}