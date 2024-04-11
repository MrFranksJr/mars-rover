import React, {FC, useEffect, useRef, useState} from "react";
import styles from '../../../styles/modal.module.css'

interface ModalProps {
    isOpen: boolean;
    hasCloseBtn?: boolean;
    onClose?: () => void;
    children: React.ReactNode;
}

const Modal: FC<ModalProps> = ({ isOpen, hasCloseBtn, onClose, children }) => {
    const [isModalOpen, setModalOpen] = useState(isOpen);
    const modalRef = useRef<HTMLDialogElement | null>(null);

    useEffect(() => {
        setModalOpen(isOpen);
    }, [isOpen]);

    useEffect(() => {
        const modalElement = modalRef.current;
        if (modalElement) {
            if (isModalOpen) {
                modalElement.showModal();
            } else {
                modalElement.close();
            }
        }
    }, [isModalOpen]);

    const handleCloseModal = () => {
        if (onClose) {
            onClose();
        }
        setModalOpen(false);
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLDialogElement>) => {
        if (event.key === "Escape") {
            handleCloseModal();
        }
    };

    return (
        <dialog ref={modalRef} className={styles.modal} onKeyDown={handleKeyDown}>
            {hasCloseBtn && (
                <svg xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 512 512" className={styles.infoCloseBtn} onClick={handleCloseModal}>
                    <path d="M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM175 175c9.4-9.4 24.6-9.4 33.9 0l47 47 47-47c9.4-9.4 24.6-9.4 33.9 0s9.4 24.6 0 33.9l-47 47 47 47c9.4 9.4 9.4 24.6 0 33.9s-24.6 9.4-33.9 0l-47-47-47 47c-9.4 9.4-24.6 9.4-33.9 0s-9.4-24.6 0-33.9l47-47-47-47c-9.4-9.4-9.4-24.6 0-33.9z"/>
                </svg>
            )}
            {children}
        </dialog>
    )
}

export default Modal