import React from "react";
import WelcomeModal from "../../Components/Modal/WelcomeModal";
import { useLocation } from "react-router-dom";

const Home: React.FC = () => {
  const location = useLocation();
  const username = location.state.username;
  const role = location.state.role;
  
  return (
    <div>
      <h1>Home</h1>
      <WelcomeModal username={username} role={role} show={true} handleClose={function (): void {
        throw new Error("Function not implemented.");
      } } />
    </div>
  );
};
export default Home;