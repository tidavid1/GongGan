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
            <img className="col-2 img-fluid" src="https://cdn-bnokp.nitrocdn.com/QNoeDwCprhACHQcnEmHgXDhDpbEOlRHH/assets/images/optimized/rev-0c79eba/www.decorilla.com/online-decorating/wp-content/uploads/2023/05/Beautiful-interior-design-for-an-office-space.jpg" alt="" />
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