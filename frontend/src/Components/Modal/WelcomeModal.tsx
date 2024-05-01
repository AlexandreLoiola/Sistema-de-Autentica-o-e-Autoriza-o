import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

interface MyModalProps {
  username: string;
  role: string;
  show: boolean;
  handleClose: () => void;
}

const WelcomeModal: React.FC<MyModalProps> = ({ username, role, show, handleClose }) => {
  return (
    <Modal show={show} onHide={handleClose} centered style={{marginTop: '-80px'}}>
      <Modal.Header style={{backgroundColor: '#6db33f', color: 'white', justifyContent: 'center'}}>
        <Modal.Title style={{fontSize: '30px'}}>Bem-vindo!</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{textAlign: 'center', fontSize: '20px'}}>
        Seja bem-vindo, <strong>{username}</strong>! Você é um <strong>{role}</strong> neste sistema.
      </Modal.Body>
      <Modal.Footer style={{justifyContent: 'center'}}>
        <Button variant="secondary" onClick={handleClose} style={{backgroundColor: '#6db33f', borderColor: '#6db33f', color: 'white', fontSize: '20px'}}>
          Fechar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default WelcomeModal;
