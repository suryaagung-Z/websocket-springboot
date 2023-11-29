let stompClient = null;
let notificationCount = 0;

$(document).ready(function () {
    connect();
    clickStatus();
    // stompClient.on('connect', function () {
    //     stompClient.send("/ws/global", {}, JSON.stringify({}));
    // });
});

function clickStatus() {
    $(".sendStatus").off().on('click', function () {
        const id = $(this).data('id');
        const status = $(this).data('status');
        stompClient.send("/ws/message", {}, JSON.stringify({ 'id': id, 'status': status }));
    });
}

function connect() {
    const socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/messages', function (message) {
            const responseData = JSON.parse(message.body);
            console.log(responseData);

            const newToastHTML = `
                <div class="toast fade show m-3" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header">
                        <i class="fa-solid fa-bell me-2"></i>
                        <strong class="me-auto">Notification</strong>
                        <small class="text-body-secondary">just now</small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${responseData.nama} has been ${responseData._status}
                    </div>
                </div>
            `;
            $('.toast-container').append(newToastHTML);

            $('#body-siswa').empty();
            responseData.siswa.forEach(function (siswa, index) {
                const isDisabledApprove = siswa.status === 1 ? 'disabled' : '';
                const isDisabledReject = siswa.status === 0 ? 'disabled' : '';

                const rowHtml = `<tr>
                                    <th scope="row">${index + 1}</th>
                                    <td>${siswa.nama}</td>
                                    <td>${siswa.kelas}</td>
                                    <td>
                                        <div>
                                            <button type="button" class="btn btn-success sendStatus" data-id="${siswa.id}" data-status="1" ${isDisabledApprove}>
                                                <i class="fa-solid fa-check"></i>
                                            </button>
                                            <button type="button" class="btn btn-danger sendStatus" data-id="${siswa.id}" data-status="0" ${isDisabledReject}>
                                                <i class="fa-solid fa-xmark"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>`;

                // Tambahkan baris ke tbody
                $('#body-siswa').append(rowHtml);
            });

            updateNotification(responseData.notifResponse);
            clickStatus();
        });

        stompClient.subscribe('/topic/global', function (res) {
            const responseData = JSON.parse(res.body);
            updateNotification(responseData);
        });

        stompClient.send("/ws/global", {}, JSON.stringify({"tes":"yoiii"}));

        // stompClient.subscribe('/topic/global-notifications', function () {
        //     notificationCount += 1;
        //     updateNotificationDisplay();
        // });
    });
}

function updateNotification(data){
    const listNotif = $('#list-notif');
    listNotif.empty();
    data.notifikasis.forEach(function (notif) {
        const notifText = `${notif.nama} has been <b>${notif.status}</b>`;
        const liElement = $('<li>').append($('<span>', { class: 'dropdown-item' }).html(notifText));
        listNotif.append(liElement);
    });
    const badge = $('#count-notif');
    badge.text(data.notifikasis.length);
}