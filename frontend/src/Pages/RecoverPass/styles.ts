import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: row;
`;

export const ContainerLeft = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 50vw;
  padding: 50px 150px 0px 150px;
`;

export const ContainerRight = styled.div`
  background-color: #000;
  width: 50vw;
  height: 100vh;
  position: absolute;
  right: 0;
`;

export const StyledTitle = styled.h1`
  font-size: 42px;
  font-weight: bold;
  margin-bottom: 2.5vh;
`;

export const StyledSubTitle = styled.span`
  font-size: 18px;
  font-weight: 500;
`;

export const StyledList = styled.ul`
  list-style-type: none;
  font-size: 17px;
  color: #000;
  margin-left: -2.5rem;
  margin-bottom: 4vh;
`;

export const StyledListItem = styled.li`
  margin-bottom: 0.2rem;
  margin-left: 1rem;
`;
