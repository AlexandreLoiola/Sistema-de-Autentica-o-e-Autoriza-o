import { Button } from "react-bootstrap";
import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20vh;
`;

export const StyledTitle = styled.h1`
  color: "#000";
  font-weight: 500;
  font-size: 84px;
`;

export const StyledSubTitle = styled.h2`
  color: "#000";
  font-weight: 400;
  font-size: 40px;
  margin-top: 1vh;
  margin-bottom: 5vh;
`;

export const StyledButton = styled(Button)``;
