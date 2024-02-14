import styled from "styled-components";
import ShieldImage from "../../assets/shield-svgrepo-com.svg";

export const Container = styled.div`
  display: flex;
  flex-direction: row;
`;

export const ContainerRight = styled.div`
  background: linear-gradient(38deg, #fff 40%, #6db33f 40%);
  width: 58vw;
  height: 100vh;
  position: absolute;
  right: 0;
`;

export const StyledImageContainerRight = styled.div`
  width: 50vw;
  height: 100vh;
  position: absolute;
  right: 0;
  z-index: 9999px;
  background-image: url(${ShieldImage});
  background-size: 80%; // Ajuste para "cover" para evitar repetições
  background-position: center; // Centraliza a imagem horizontalmente e verticalmente
  background-repeat: no-repeat; // Evita repetições da imagem
`;

export const StyledTitle = styled.h1`
  font-size: 42px;
  font-weight: bold;
  margin-bottom: 6vh;
`;
