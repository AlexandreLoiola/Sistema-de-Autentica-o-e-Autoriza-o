import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

interface MyModalProps {
  username: string;
  handleClose: () => void;
}

const RegistredUserModal: React.FC<MyModalProps> = ({ username, handleClose }) => {
  return (
    <Modal show={true} onHide={handleClose} centered style={{marginTop: '-80px'}}>
      <Modal.Header style={{backgroundColor: '#6db33f', color: 'white', justifyContent: 'center'}}>
        <Modal.Title style={{fontSize: '30px'}}>Bem-vindo!</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{textAlign: 'center', fontSize: '20px'}}>
        O usuário <strong>{username}</strong> foi cadastrado!
      </Modal.Body>
      <Modal.Footer style={{justifyContent: 'center'}}>
        <Button variant="secondary" onClick={handleClose} style={{backgroundColor: '#6db33f', borderColor: '#6db33f', color: 'white', fontSize: '20px'}}>
          Fechar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RegistredUserModal;
