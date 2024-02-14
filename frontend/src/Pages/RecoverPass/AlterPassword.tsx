import React from "react";
import {
  Container,
  ContainerLeft,
  StyledTitle,
} from "./styles";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import PasswordInput from "../../Components/Forms/PasswordForm";
import ShieldContainer from "../../Components/ShieldContainer/ShieldContainer";
import Footer from "../../Components/Footer/Footer";

const AlterPassword: React.FC = () => {
  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Redefinir Senha</StyledTitle>
        <FormContainer style={{ marginTop: "6vh" }}>
          <PasswordInput
            label={"Senha"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
          <PasswordInput
            label={"Confirmar Senha"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
          <StyledFormButton style={{ width: "100%" }}>
            ALTERAR SENHA
          </StyledFormButton>
        </FormContainer>
      </ContainerLeft>
      <ShieldContainer />
      <Footer />
    </Container>
  );
};
export default AlterPassword;
