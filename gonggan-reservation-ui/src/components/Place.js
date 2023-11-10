export function Place(props) {
    const placeId = props.placeId;
    const name = props.name;
    const address = props.address;
    const category = props.category;
    const description = props.description;

    const handleReservationBtnClicked = e => {
        props.onAddClick(placeId, name, address, description);
    }

    return <>
        <div class="row">
            <img className="col-2 img-fluid" src="https://i.imgur.com/ihrQrhy_d.webp?maxwidth=760&fidelity=grand" alt="" />
            <div className="col-7">
                <div className="row">{name}</div>
                <div className="row">{address}</div>
                <div className="row text-muted">{category}</div>
            </div>
            <div className="col text-end action">
                <button onClick={handleReservationBtnClicked} className="btn btn-small btn-outline-dark">예약하기</button>
            </div>
        </div>
    </>
}