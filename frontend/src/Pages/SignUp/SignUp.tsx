import React, { useState } from "react";
import { Container, ContainerLeft, StyledLink, StyledTitle } from "./styles";
import PasswordInput from "../../Components/Forms/PasswordForm";
import TextForm from "../../Components/Forms/TextForm";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import CheckboxForm from "../../Components/Forms/CheckboxForm";
import ShieldContainer from "../../Components/ShieldContainer/ShieldContainer";
import Footer from "../../Components/Footer/Footer";
import GoogleRegistrationButton from "../../Components/Buttons/GoogleRegistration";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import RegistredUserModal from "../../Components/Modal/RegistredUserModel";

const SignUp: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');
  const [showModal, setShowModal] = useState<boolean>(false);

  const navigate = useNavigate();

  const handleRegistration = async (
    username: string,
    email: string,
    password: string
  ): Promise<void> => {
    try {
      axios
        .post(`http://localhost:8080/api/management/users`, {
          username: username,
          email: email,
          password: password,
        })
        .then(() => {
          setShowModal(true);
        })
        .catch((error) => {
          console.log(error);
          alert("Credenciais Inválidas!");
        });
    } catch (error) {
      console.error(error);
    }
  };

  const validatePassword = (): boolean => {
    return password === confirmPassword;
  };

  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Cadastro</StyledTitle>
        <FormContainer style={{}}>
          <TextForm
            label={"Nome de Usuário"}
            placeHolder={""}
            message={""}
            value={username}
            onInputChange={(value) => setUsername(value)}
          />
          <TextForm
            label={"E-mail"}
            placeHolder={""}
            message={""}
            value={email}
            onInputChange={(value) => setEmail(value)}
          />
          <PasswordInput
            label={"Senha"}
            placeHolder={""}
            message={""}
            value={password}
            onInputChange={(value) => setPassword(value)}
          />
          <PasswordInput
            label={"Confirmar Senha"}
            placeHolder={""}
            message={""}
            value={confirmPassword}
            onInputChange={(value) => setConfirmPassword(value)}
          />
          <StyledFormButton
            style={{ width: "100%" }}
            onClick={() => {
              validatePassword();
              handleRegistration(username, email, password);
            }}
          >
            ENTRAR
          </StyledFormButton>
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
              onInputChange={() => {}}
            />
          </div>
          <GoogleRegistrationButton />
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
      <Footer />
      {showModal && (
        <RegistredUserModal
          username={username}
          handleClose={() => {
            setShowModal(false);
            navigate("/signin", {
              state: {
                email: email,
              },
            });
          }}
        />
      )}
    </Container>
  );
};
export default SignUp;
