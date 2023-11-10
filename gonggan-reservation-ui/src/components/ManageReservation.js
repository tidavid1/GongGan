import moment from "moment";

export function ManageReservation(props) {
    const name = props.places.find(v => v.placeId === props.placeId).name;
    const reservationDate = moment(props.startAt).format("YYYY-MM-DD");
    const StartAtHour = moment(props.startAt).hour();
    const EndAtHour = moment(props.endAt).hour();
    return (
        <>
            <div class='row'>
                <div className="col">예약 장소: {name}</div>
                <div className="col">예약 일자: {reservationDate}</div>
                <div className="col">예약 시작 시간: {StartAtHour}</div>
                <div className="col">예약 종료 시간: {EndAtHour}</div>
            </div>
        </>
    )

}