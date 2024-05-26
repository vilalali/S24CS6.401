import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import Footer from "./components/footer/Footer";
import Home from "./components/home/Home";
import Converter from "./components/converter/Converter";
import About from "./components/about/About";
import ContactUs from "./components/contact/ContactUs";
import Register from "./components/login/Register";
import Login from "./components/login/Login";

const App = () => {
  return (
    <>
      <BrowserRouter>
        <div className="min-h-screen flex flex-col">
          <main className="flex-grow">
            <Navbar />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/converter" element={<Converter />} />
              <Route path="/about" element={<About />} />
              <Route path="/contact" element={<ContactUs />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
            </Routes>
          </main>
          <Footer />
        </div>
      </BrowserRouter>
    </>
  );
};

export default App;
