import React, { useState } from "react";
import { InputGroup } from "react-bootstrap";
import {
  StyledFormControl,
  StyledFormGroup,
  StyledFormLabel,
  EyeOpenIcon,
  EyeCloseIcon,
  ButtonSeePass,
} from "./styles";

interface IProps {
  label: string;
  placeHolder: string;
  message: string;
  value?: string | number;
  style?: React.CSSProperties;
  onInputChange: (value: string) => void;
}

const PasswordInput: React.FC<IProps> = ({
  label,
  placeHolder,
  message,
  value,
  onInputChange,
}) => {
  const [showPassword, setShowPassword] = useState(false);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onInputChange(event.target.value);
  };

  const handleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <StyledFormGroup className="mb-3" style={{ width: "20%" }}>
      <StyledFormLabel>{label}</StyledFormLabel>
      <StyledFormControl
        type={showPassword ? "text" : "password"}
        value={value}
        placeholder={placeHolder}
        onChange={handleInputChange}
      />
      <InputGroup
        style={{
          display: "flex",
          flexDirection: "row-reverse",
        }}
      >
        <ButtonSeePass variant="outline-secondary" onClick={handleShowPassword}>
          {showPassword ? <EyeCloseIcon /> : <EyeOpenIcon />}
        </ButtonSeePass>
      </InputGroup>
      <p className="error-message">{message}</p>
    </StyledFormGroup>
  );
};

export default PasswordInput;
