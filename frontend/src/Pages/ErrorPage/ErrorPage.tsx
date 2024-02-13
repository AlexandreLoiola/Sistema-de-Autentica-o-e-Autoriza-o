import React from "react";
import { useNavigate } from "react-router-dom";
import { Container, StyledButton, StyledSubTitle, StyledTitle } from "./styles";

const ErrorPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Container>
      <StyledTitle>404 :( </StyledTitle>
      <StyledSubTitle>
        Oops! Esta página não pode ser encontrada!
      </StyledSubTitle>
      <StyledButton variant="primary" size="lg" onClick={() => navigate("/")}>
        Voltar para Home
      </StyledButton>
    </Container>
  );
};
export default ErrorPage;
