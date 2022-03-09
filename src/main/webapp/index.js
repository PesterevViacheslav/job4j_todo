function validate() {
    let res = true;
    $('.container input').each(function() {
        let $this = $(this);
        if ($this.val() == "") {
            $this.css('border', '1px solid red');
            $this.attr("placeholder", "Field must be filled out");
            $this.addClass('error');
            if (res) {
                res = false;
            }
        } else {
            $this.css('border', '#ccc');
        }
    });
    return res;
}
function createTr(item) {
    let tr = `<tr>`
        + `<td>${item.id}</td>`
        + `<td>${item.description}</td>`
        + `<td>${item.created}</td>`;
    if (item.done) {
        tr = tr + "<td>Выполнено</td><td><input type=\"checkbox\" name=\"isDone\" id=\"" + item.id + "\" disabled = true/></td>";
    } else {
        tr = tr + "<td>Не выполнено</td><td><input type=\"checkbox\" name=\"isDone\" id=\"" + item.id + "\"/></td>";
    }
    tr = tr + "</tr>";
    $('#table tbody').append(tr);
}

function getItems() {
    let chkAll;
    if ($('#checkAll').is(':checked')) {
        chkAll = 1;
    } else {
        chkAll = 0;
    }
    $("#tbody").empty();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/item',
        data: {
            "all" : chkAll
        },
        dataType: 'json'
    }).done(function(data) {
        for (let item of data) {
            createTr(item);
        }
    }).fail(function(err){
        console.log(err);
    });
    return false;
}

function setDone() {
    let chkAll;
    if ($('#checkAll').is(':checked')) {
        chkAll = 1;
    } else {
        chkAll = 0;
    }
    let ids = '[';
    $('#tbody tr input:checked').each(function(){
        ids += $(this).attr('id') + ',';
    })
    ids = ids.substring(0,(ids.length-1));
    ids += ']';
    if(ids == "]") {
        ids = "[-1]";
    }
    $("#tbody").empty();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/item',
        data: { "ids" : ids, "all" : chkAll},
        dataType: 'json'
    }).done(function(data) {
        for (let item of data) {
            createTr(item) ;
        }
    }).fail(function(err){
        console.log(err);
    });
    return false;
}