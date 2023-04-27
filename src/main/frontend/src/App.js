import React, {useEffect, useState} from "react";
import axios from "axios";
import "./App.css";
import "./title.scss";
import LoadingSpinner from "./LoadingSpinner";

const ShopTypes = () => {

    const [shopTypes, setShopTypes] = useState([]);
    const [itemList, setItemList] = useState([""]);
    const [shopType, setShopType] = useState("Grocery");
    const [serverResponse, setServerResponse] = useState(
        {items: [{itemName: "", reasoning: ""}], comments: ""}
    );
    const [isLoading, setIsLoading] = useState(false);


    const fetchShopTypes = () => {
        axios.get("http://localhost:8080/api/v1/shops").then(res => {
            console.log(res);
            setShopTypes(res.data);
        })

    };

    useEffect(() => {
        fetchShopTypes();
    }, []);

    const handleItemChange = (e, index) => {
        const {value} = e.target;
        const list = [...itemList];
        list[index] = value;
        setItemList(list);
    };

    const handleItemRemove = (index) => {
        const list = [...itemList];
        list.splice(index, 1);
        setItemList(list);
    };

    const handleItemAdd = () => {
        setItemList([...itemList, ""]);
    };

    function handleSelectChange(event) {
        setShopType(event.target.value)
    }

    const handleSubmit = async () => {
        console.log(shopType);
        console.log(itemList);

        try {
            setIsLoading(true);
            // make axios post request
            const response = await axios({
                method: "post",
                url: "http://localhost:8080/api/v1/answer",
                data: {
                    shopType: shopType,
                    items: itemList.toString()
                },
                timeout: 35000
            });
            console.log(response);
            setServerResponse(response.data);
            setIsLoading(false);
        } catch (error) {
            console.log(error);
            setIsLoading(false);
        }
    }

    return (
        <div className="container-fluid">
            <svg>
                <symbol id="s-text">
                    <text textAnchor="middle"
                          x="50%"
                          y="35%"
                          className="text--line"
                    >
                        What's
                    </text>
                    <text textAnchor="middle"
                          x="50%"
                          y="68%"
                          className="text--line2"
                    >
                        N E X T
                    </text>

                </symbol>

                <g className="g-ants">
                    <use xlinkHref="#s-text"
                         className="text-copy"></use>
                    <use xlinkHref="#s-text"
                         className="text-copy"></use>
                    <use xlinkHref="#s-text"
                         className="text-copy"></use>
                    <use xlinkHref="#s-text"
                         className="text-copy"></use>
                    <use xlinkHref="#s-text"
                         className="text-copy"></use>
                </g>


            </svg>

            <p className="text-sm-center text-secondary h6 mb-4">
                WhatIsNext is a recommendation engine that suggests additional products a customer may be interested in
                based on their past purchases at a specific type of store.
                By analyzing data on common purchases and customer behaviors, WhatIsNext provides accurate and
                personalized recommendations for products
                that the customer may not have considered before, helping to increase customer satisfaction and boost
                sales for the store.
            </p>

            <form className="form-field" onSubmit={e => {
                e.preventDefault();
            }}>
                <label htmlFor="shopType" className="form-label">Select The Shop Type</label>
                <select id="shopType" name="shopType" className="form-control form-select"
                        defaultValue="Grocery"
                        onChange={handleSelectChange}>
                    {shopTypes.map((shop, index) => {
                        return <option key={index} value={shop}>{shop}</option>
                    })}
                </select>

                <label htmlFor="item">Enter Item(s)</label>
                {itemList.map((item, index) => (
                    <div key={index} className="items">
                        <div className="first-division">
                            <input
                                name="item"
                                type="text"
                                id="item"
                                value={item.item}
                                onChange={(e) => handleItemChange(e, index)}
                                required
                            />
                            {itemList.length - 1 === index && itemList.length < 5 && (
                                <button
                                    type="button"
                                    onClick={handleItemAdd}
                                    className="add-btn"
                                >
                                    <span>Add An Item</span>
                                </button>
                            )}
                        </div>
                        <div className="second-division">
                            {itemList.length !== 1 && (
                                <button
                                    type="button"
                                    onClick={() => handleItemRemove(index)}
                                    className="remove-btn"
                                >
                                    <span>Remove</span>
                                </button>
                            )}
                        </div>
                    </div>
                ))}

                <button className="button submit-btn" type="submit" onClick={handleSubmit} disabled={isLoading}>Submit
                </button>
            </form>
            <hr></hr>
            {isLoading ? <LoadingSpinner/> : false}
            <h4>
                {serverResponse.comments}
            </h4>
            <table className="table table-borderless">
                <tbody>
                {
                    serverResponse.items !== null ?
                        serverResponse.items.map((item, index) => {
                            return (
                                <tr key={index}>
                                    <td><strong>{item.itemName}</strong></td>
                                    <td>{item.reasoning}</td>
                                </tr>
                            )
                        }) :
                        false
                }
                </tbody>
            </table>
        </div>
    )
}

function App() {
    return (
        <div className="App">
            <ShopTypes/>
        </div>
    );
}

export default App;
