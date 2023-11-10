import moment from "moment";
import { Fragment } from "react";
import { ManageReservation } from "./ManageReservation";

export function ManageReservationList({ reservations = [], places = []}) {
    return (
        <Fragment>
            <h5>예약 리스트</h5>
            <ul className="list-group reservations">
                {reservations.filter(reservation => reservation.status === "APPROVED").map(v => <li key={v.reservationId} className="">
                    <ManageReservation {...v} places={places}/>
                </li>)}
            </ul>
        </Fragment>
    )
}