import { Fragment } from "react";
import { Reservation } from "./Reservation";
import moment from "moment";

export function ReservationList({ reservations = [] , date}) {
    return (
        <Fragment>
            <h5>예약 시간</h5>
            <ul className="list-group reservations">
                {reservations.filter(reservation => reservation.status === "APPROVED")
                    .filter(reservation => (moment(reservation.startAt).format("YYYY-MM-DD") === moment(date).format("YYYY-MM-DD") || moment(reservation.endAt).format("YYYY-MM-DD") === moment(date).format("YYYY-MM-DD")))
                    .map(v => <li key={v.reservationId} className="">
                        <Reservation {...v} />
                    </li>)}
            </ul>
        </Fragment>
    )
}