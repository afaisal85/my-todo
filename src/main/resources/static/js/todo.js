var GET_TASKS_ENDPOINT = "/api/todos/get";
var CREATE_TASK_ENDPOINT = "/api/todos/create";
var DONE_TASK_ENDPOINT = "/api/todos/done";
var CLEAN_TASK_ENDPOINT = "/api/todos/clean";

function getTasks() {
    $.get(GET_TASKS_ENDPOINT, function(response, status, xhr) {
        if (xhr.status !== 200) {
            // Noop
        } else {
            var $elem = $("#your-todo #todo");
            $elem.empty();

            response.forEach(function(todo) {
                $($elem).append("<tr><td><div id=" + todo.id + " class='task " + todo.isCompleted + "'>" + todo.taskName + "  <a href='#' class='done' data-id='" + todo.id + "' data-name='" + todo.taskName + "'>☑</a></div></td></tr>");
            });

            attachDoneHandler();
        }
    });
}

function createTasks(taskName) {
    var data = JSON.stringify({
        taskName: taskName
    });

    var ajaxConfig = {
        type: 'POST',
        url: CREATE_TASK_ENDPOINT,
        contentType: 'application/json; charset=UTF-8',
        data: data
    };

    $.ajax(ajaxConfig)
        .done(function() {
            getTasks();
        }).fail(function() {
        // NOop
    })
}

function updateTasks(id, taskName) {
    var ajaxConfig = {
        type: 'PUT',
        url: DONE_TASK_ENDPOINT,
        data: {id: id,
            taskName: taskName}
    };

    $.ajax(ajaxConfig)
        .done(function() {
            getTasks();
        }).fail(function(ex) {
        // NOop
    })
}

function attachDoneHandler() {
    // 3. Add the event handler for updating task
    $("#your-todo #todo a").on('click', function(e) {
        var id = e.target.dataset.id;
        var taskName = e.target.dataset.name;

        updateTasks(id, taskName);
    });
}

$(document).ready(function() {
    // 1. Once the page load, hit the get tasks API
    getTasks();

    // 2. Add the event handler for form submit
    var $form = $("#todo-form");
    $($form).submit(function(e) {
        e.preventDefault();
        var taskname = e.target[0].value;
        createTasks(taskname);
    });

    // 4. Attach the on click event handler for remove-completed button
    var $cleanButton = $("#remove-completed");
    $($cleanButton).on('click', function(e) {
        var ajaxConfig = {
            type: 'PUT',
            url: CLEAN_TASK_ENDPOINT
        };

        $.ajax(ajaxConfig)
            .done(function() {
                getTasks();
            }).fail(function(ex) {
            // NOop
        })
    });
});