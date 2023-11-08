import { useState } from "react";
import TimePicker from "react-time-picker";
import 'react-time-picker/dist/TimePicker.css';

export function NewReservation({placeId, onReservationSubmit}) {
    const [reservation, setReservation] = useState({
        placeId: placeId.placeId, email: "", startAt: "00:00", endAt: "00:00"
    })
    const [startAtTime, setStartAtTime] = useState('10:00')
    const [endAtTime, setEndAtTime] = useState('11:00')
    const handleEmailInputChanged = (e) => setReservation({ ...reservation, email: e.target.value })
    const handleSubmit = (e) => {
        if (reservation.email === "") {
            alert("이메일 입력값을 확인하세요!")
        } else {
            setReservation({...reservation, startAt: startAtTime.valueOf(), endAt: endAtTime.valueOf()})
            onReservationSubmit(reservation);
        }
    }
    return (<>
        <div class='row'>
            <div class='col'>
                <h5>시작 시간</h5>
                <TimePicker onChange={(value) => setStartAtTime(value)} value={startAtTime}/>
            </div>
            <div class='col'>
                <h5>종료 시간</h5>
                <TimePicker onChange={(value) => setEndAtTime(value)} value={endAtTime}/>
            </div>
        </div>
        <form>
            <div className="mb-3">
                <label htmlFor="email" className="form-label">이메일</label>
                <input type="email" className="form-control mb-1" value={reservation.email} onChange={handleEmailInputChanged}
                    id="email" />
            </div>
        </form>
        <button type="button" class="btn btn-primary" onClick={handleSubmit}>예약하기</button>
    </>

    )
}