import { Fragment } from "react";
import { Place } from "./Place";


export function PlacesList({ places = [], onAddClick }) {
    return (
        <Fragment>
            <ul className="list-group places">
                {places.map(v => <li key={v.placeId} className="">
                    <Place {...v} onAddClick={onAddClick}/><hr/>
                </li>)}
            </ul>
        </Fragment>
    )
}