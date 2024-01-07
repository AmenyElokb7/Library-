import { useEffect, useState } from 'react';
import MessageModel from '../../../models/MessageModel';
import { SpinnerLoading } from '../../Utils/SpinnerLoading';
import { Pagination } from '../../Utils/Pagination';

export const Messages = () => {
    const [isLoadingMessages, setIsLoadingMessages] = useState(true);
    const [httpError, setHttpError] = useState<string | null>(null);
    const [messages, setMessages] = useState<MessageModel[]>([]);
    const [messagesPerPage] = useState(5);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const fetchUserMessages = async () => {
            // Assuming the user details are stored under the 'user' key in local storage
            const userString = localStorage.getItem('user');
            if (userString) {
                const userDetails = JSON.parse(userString);
                // Make sure you have the 'username' in the userDetails object
                const userEmail = userDetails.username;
                
                if (userEmail) {
                    const url = `http://127.0.0.1:8083/api/messages/search/findByUserEmail?userEmail=${encodeURIComponent(userEmail)}&page=${currentPage - 1}&size=${messagesPerPage}`;
                    const requestOptions = {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Basic ' + localStorage.getItem('basicAuth'),
                            'Content-Type': 'application/json'
                        }
                    };
                    try {
                        const messagesResponse = await fetch(url, requestOptions);
                        if (!messagesResponse.ok) {
                            throw new Error('Something went wrong!');
                        }
                        const messagesResponseJson = await messagesResponse.json();
                        setMessages(messagesResponseJson._embedded.messages);
                        setTotalPages(messagesResponseJson.page.totalPages);
                    } catch (error: any) {
                        setIsLoadingMessages(false);
                        setHttpError(error.message);
                    }
                }
            } else {
                // Handle the case where user details are not found in local storage
                console.error('User details are not found in the local storage.');
                setHttpError('Authentication details not found. Please log in.');
            }
            setIsLoadingMessages(false);
        };

        fetchUserMessages();
        window.scrollTo(0, 0);
    }, [currentPage, messagesPerPage]);

    if (isLoadingMessages) {
        return <SpinnerLoading />;
    }

    if (httpError) {
        return (
            <div className='container m-5'>
                <p>Error: {httpError}</p>
            </div>
        );
    }

    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    return (
        <div className='mt-2'>
            {messages.length > 0 ? (
                <>
                    <h5>Current Q/A: </h5>
                    {messages.map((message) => (
                        <div key={message.id}>
                            <div className='card mt-2 shadow p-3 bg-body rounded'>
                                <h5>Case #{message.id}: {message.title}</h5>
                                <h6>{message.userEmail}</h6>
                                <p>{message.question}</p>
                                <hr />
                                <div>
                                    <h5>Response: </h5>
                                    {message.response && message.adminEmail ? (
                                        <>
                                            <h6>{message.adminEmail} (admin)</h6>
                                            <p>{message.response}</p>
                                        </>
                                    ) : (
                                        <p><i>Pending response from administration. Please be patient.</i></p>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))}
                </>
            ) : (
                <h5>All questions you submit will be shown here.</h5>
            )}
            {totalPages > 1 && (
                <Pagination currentPage={currentPage} totalPages={totalPages} paginate={paginate} />
            )}
        </div>
    );
};
