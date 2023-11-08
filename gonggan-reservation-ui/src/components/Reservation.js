import moment from "moment";

export function Reservation(props) {
    const StartAtHour = moment(props.startAt).hour();
    const EndAtHour = moment(props.endAt).hour();
    return <>
        {StartAtHour}시 ~ {EndAtHour}시
    </>
}