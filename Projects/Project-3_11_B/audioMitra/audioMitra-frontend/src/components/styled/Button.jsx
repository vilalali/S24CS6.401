const Button = ({ buttonName, handleClick }) => {
    
    return (
      <>
        <button className="border border-blue-400 bg-blue-200 hover:bg-gray-200 px-2 py-1 mx-1 my-1" onClick={handleClick && (() => handleClick())}>
          {buttonName}
        </button>
      </>
    );
  };
  
export default Button;
  