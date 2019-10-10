function postComment() {
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": $("#questionId").val(),
            "content": $("#comment").val(),
            "type": 1
        }),
        success:function(response) {
            if (response.code == 200) {

            } else {
                alert(response.message);
            }
        },
        dataType:"json"
    });
}