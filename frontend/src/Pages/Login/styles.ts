import { Link } from "react-router-dom";
import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: row;
`;

export const ContainerLeft = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 50vw;
  padding: 50px 150px 0px 150px;
`;


export const StyledTitle = styled.h1`
  font-size: 42px;
  font-weight: bold;
  margin-bottom: 6vh;
`;

export const StyledLink = styled(Link)`
  color: #0d6efd;

  &:hover {
    color: #467025;
  }
`;
