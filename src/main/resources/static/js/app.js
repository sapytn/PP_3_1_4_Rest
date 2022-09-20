getUsersTable();

async function findAllUsers() {
    let allUsers = await fetch('api/users' );
    return allUsers;
}

function createRow(user ,role) {
    let row = `<tr> <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map((role)=>role.name)}</td>
                        <td>
                            <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info text-white btn-sm edit-btn"
                            data-toggle="modal" data-target="#editmodal">Edit</button></td>
                        <td>
                            <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger btn-sm del-btn"
                            data-toggle="modal" data-target="#deletemodal">Delete</button></td>
                   </tr>`
    return row;
}

async function getUsersTable() {
    const usersList = document.querySelector(".usershow");
    let output = '';
    findAllUsers()
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                output += createRow(user);
            })
            usersList.innerHTML = output;
        })
}

function getUserRolesForEdit() {
    let allRoles = [];
    $.each($("select[name='roles'] option:selected"), function() {
        let role = {};
        role.id = $(this).attr('id');
        role.name = $(this).attr('name');
        allRoles.push(role);
    });
    return allRoles;
}

$(document).on('click', '.edit-btn', async function() {
    const userId = $(this).attr('data-userid');
    await fetch('api/users/' + userId)
        .then(res => res.json())
        .then(user => {
            $('#id1').val(user.id);
            $('#firstname1').val(user.firstName);
            $('#lastname1').val(user.lastName);
            $('#age1').val(user.age);
            $('#email1').val(user.email);
            $('#password1').val(user.password);
        })
})



$('#editbutton').on('click', (e) => {
    e.preventDefault();

    let userEditId = $('#id1').val();

    var editUser = {
        id: $("input[name='id1']").val(),
        firstName: $("input[name='firstname1']").val(),
        lastName: $("input[name='lastname1']").val(),
        age: $("input[name='age1']").val(),
        email: $("input[name='email1']").val(),
        password: $("input[name='password1']").val(),
        roles: getUserRolesForEdit()
    }
    console.log(editUser);


    fetch('/api/users', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(editUser)
    }).then(async function (response) {
        let newRow = createRow(editUser);
        $('#mainTableWithUsers').find('#' + userEditId).replaceWith(newRow);
        $('#editmodal').modal('hide');
        getUsersTable();
        $('#nav-home-tab').tab('show');
    })
});

$(document).on('click', '.del-btn', async function () {
    let userId = $(this).attr('data-userid');
    await fetch('api/users/' + userId)
        .then(res => res.json())
        .then(user => {
            $('#userid2').val(user.id);
            $('#firstname2').val(user.firstName);
            $('#lastname2').val(user.lastName);
            $('#age2').val(user.age);
            $('#email2').val(user.email);
            $('#password2').val(user.password);
            $('#role2').val(getUserRolesForEdit());
        })
});

$('#deletebutton').on('click', (e) => {
    e.preventDefault();
    let userId = $('#userid2').val();
    fetch('/api/users/' + userId, {
        method: 'DELETE',
    }).then(async function (response) {
        $('#mainTableWithUsers').find('#' + userId).replaceWith('');
        getUsersTable();
        $('#deletemodal').modal('hide');
        $('#nav-home-tab').tab('show');
    })
});

function getUserRolesForAdd() {
    let allRoles = [];
    $.each($("select[name='newroles'] option:selected"), function() {
        let role = {};
        role.id = $(this).attr('value');
        role.role = $(this).attr('id');
        allRoles.push(role);
    });
    return allRoles;
}


$(".addnewuser").on('click', async function (e) {
    e.preventDefault();
    let newUser = {
        firstName: $("input[name='newfirstname']").val(),
        lastName: $("input[name='newlastname']").val(),
        age: $("input[name='newage']").val(),
        email: $("input[name='newemail']").val(),
        password: $("input[name='newpassword']").val(),
        roles: getUserRolesForAdd()
    }

    console.log(newUser.firstName + ' ' + newUser.email);


    await fetch('api/users', {
        method: "POST",
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    }).then(async function () {
        getUsersTable();
        $('#nav-home-tab').tab('show');
    }).then(function () {
        $('#newfirstname').empty().val('')
        $('#newlastname').empty().val('')
        $('#newage').empty().val('')
        $('#newemail').empty().val('')
        $('#newpassword').empty().val('')
        console.log("helloworld")
    })
})