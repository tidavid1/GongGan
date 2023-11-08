import './App.css';
import "bootstrap/dist/css/bootstrap.css";
import { useEffect, useState } from 'react';
import { PlacesList } from './components/PlacesList';
import { Nav } from './components/Nav';
import axios from 'axios';
import ReactModal from 'react-modal';
import { ReservationList } from './components/ReservationList';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import moment from 'moment';
import { NewReservation } from './components/NewReservation';
function App() {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [places, setPlaces] = useState([
    { placeId: 1, name: '공유오피스', address: '테스트 주소', category: 'office' },
    { placeId: 2, name: '공유 주방', address: '테스트 주소', category: 'kitchin' },
  ]);
  const [place, setPlace] = useState(
    { placeId: 1, name: '공유오피스', address: '테스트 주소', category: 'office' }
  )
  const handleReservationClick = (placeId, name, address, description) => {
    setModalIsOpen(true)
    setPlace({ placeId, name, address, description })
    axios.get('http://localhost:8080/api/v1/reservations?placeId=' + placeId).then(v => setReservations(v.data.data))
  }
  const [value, onChange] = useState(new Date());
  const [reservations, setReservations] = useState([]);

  const handleReservationSubmit = (reservation) => {
    console.log(reservation.placeId);
    axios.post("http://localhost:8080/api/v1/reservations", {
      placeId: reservation.placeId,
      email: reservation.email,
      startAt: moment(value).format("YYYY-MM-DD")+"T"+reservation.startAt,
      endAt: moment(value).format("YYYY-MM-DD")+"T"+reservation.endAt
    }).then(
      v => alert("예약이 정상적으로 접수되었습니다."),
      e => {
        console.log(e);
        alert(e.response.data.data);
      }
    )
  }

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/places').then(v => setPlaces(v.data.data));
  }, []);

  return (

    <div>
      <Nav />
      <div className="container-fluid">
        <PlacesList places={places} onAddClick={handleReservationClick}></PlacesList>
      </div>
      <ReactModal class="modal fade" isOpen={modalIsOpen}>
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              <h2>{place.name}</h2>
              <figure>
                <blockquote class="blockquote">
                  <p class="mb-0">{place.address}</p>
                </blockquote>
                <figcaption class="blockquote-footer">{place.description}</figcaption>
              </figure>
              <div class="row">
                <img className='col-2' src='https://i.imgur.com/n9ekICW.jpeg' alt='' />
                <img className='col-2' src='https://i.imgur.com/n9ekICW.jpeg' alt='' />
                <img className='col-2' src='https://i.imgur.com/n9ekICW.jpeg' alt='' />
              </div>
              <hr />
              <h4>예약 현황</h4>
              <div class="row">
                <div class="col">
                  <Calendar onChange={onChange} onClickDay={() => { 
                    ReservationList(reservations, moment(value).date())
                    }} calendarType='gregory' value={value} />
                </div>
                <div class="col">
                  <ReservationList reservations={reservations} date={value} />
                </div>
                <div class="col">
                  <NewReservation placeId={place.placeId} onReservationSubmit={handleReservationSubmit}/>
                </div>
              </div>
            </div>
            <div class="modal-footer">
            
              <button type="button" class="btn btn-secondary" onClick={() => setModalIsOpen(false)}>Close</button>

            </div>
          </div>
        </div>
      </ReactModal>
    </div>
  );
}

export default App;
