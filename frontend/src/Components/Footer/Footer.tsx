import React from "react";
import { StyledFixedContainer, StyledText } from "./styles";

const Footer: React.FC = () => {
  return (
    <StyledFixedContainer>
      <StyledText>
        Desevolvido por{" "}
        <a href="https://alexandreloiola.galatus.com.br">Alexandre Loiola</a>
      </StyledText>
    </StyledFixedContainer>
  );
};
export default Footer;
