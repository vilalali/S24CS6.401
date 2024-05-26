import "./Popup.css";

function Popup({ setOpenModal, message }) {

  return (
    <div className="popUpBackground">
      <div className="modalContainerPopup flex flex-nowrap justify-between text-md">
        <h4 className="text-sm">{message}</h4>
        <div className="titleCloseBtn11 text-right">
          <button
            className="rounded-full"
            onClick={() => {
              setOpenModal(false);
            }}
          >
            X
          </button>
        </div>

      </div>
    </div>

  );
}

export default Popup;
