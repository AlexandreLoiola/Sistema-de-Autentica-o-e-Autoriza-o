import React from "react";
import {
  Container,
  ContainerLeft,
  StyledLink,
  StyledTitle,
} from "./styles";
import PasswordInput from "../../Components/Forms/PasswordForm";
import TextForm from "../../Components/Forms/TextForm";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import CheckboxForm from "../../Components/Forms/CheckboxForm";
import ShieldContainer from "../../Components/ShieldContainer/ShieldContainer";

const SignUp: React.FC = () => {
  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Cadastro</StyledTitle>
        <FormContainer style={{}}>
          <TextForm
            label={"Usuário"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
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
          <StyledFormButton style={{ width: "100%" }}>ENTRAR</StyledFormButton>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              width: "100%",
              marginTop: "1.5vh",
              justifyContent: "space-between",
            }}
          >
            <CheckboxForm
              label={"Li e aceito os termos e condições"}
              message={""}
              onInputChange={function (value: string): void {
                throw new Error("Function not implemented.");
              }}
            />
          </div>
          <div
            style={{
              width: "86%",
              borderTop: "1px solid #00000058",
              marginTop: "4vh",
              marginBottom: "6vh",
            }}
          ></div>
          <span>
            Já possui conta? <StyledLink to="/signin">Clique aqui</StyledLink>
          </span>
        </FormContainer>
      </ContainerLeft>
      <ShieldContainer />
    </Container>
  );
};
export default SignUp;
